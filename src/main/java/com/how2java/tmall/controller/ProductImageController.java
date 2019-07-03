package com.how2java.tmall.controller;

import com.how2java.tmall.bean.Product;
import com.how2java.tmall.bean.ProductImage;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 10:52
 **/
@Controller
@RequestMapping("")
public class ProductImageController {
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private ProductService productService;

  @RequestMapping("admin_productImage_add")
  public String add(UploadFile uploadFile, ProductImage productImage, HttpSession session) {
    productImageService.add(productImage);
    /*根据类型将上传图片文件存储到不同文件夹*/
    MultipartFile image = uploadFile.getImage();
    try {
      if (ProductImageService.type_single.equals(productImage.getType())) {
        singleImageProcess(session, image, productImage.getId());
      } else {
        detailImageProcess(session, image, productImage.getId());
      }
    }catch (IOException e) {
      e.printStackTrace();
    }
    return "redirect:/admin_productImage_list?pid="+productImage.getPid();
  }

  @RequestMapping("admin_productImage_delete")
  public String delete(ProductImage productImage, HttpSession session) {
    productImage = productImageService.get(productImage.getId());
    productImageService.delete(productImage);
    /*删除服务器内存储的图片文件*/
    if (ProductImageService.type_single.equals(productImage.getType())) {
      deleteImageFile("img/productSingle", productImage.getId(), session);
      deleteImageFile("img/productSingle_small", productImage.getId(), session);
      deleteImageFile("img/productSingle_middle", productImage.getId(), session);
    } else {
      deleteImageFile("img/productDetail", productImage.getId(), session);
    }
    return "redirect:/admin_productImage_list?pid=" + productImage.getPid();
  }

  @RequestMapping("admin_productImage_list")
  public String list(int pid, Model model) {
    List<ProductImage> singleImage = productImageService.listSingle(pid);
    List<ProductImage> detailImage = productImageService.listDetail(pid);
    Product product = productService.get(pid);
    model.addAttribute("p", product);
    model.addAttribute("pisSingle", singleImage);
    model.addAttribute("pisDetail", detailImage);
    return "admin/listProductImage";
  }

  private void singleImageProcess(HttpSession session,MultipartFile image, int id) throws IOException {
    File imageFolder = new File(session.getServletContext().getRealPath("img/productSingle"));
    File file = ImageUtil.imageFileProcess(imageFolder, image, id);
    resizeImageFile(file, "img/productSingle_small", session.getServletContext(),56,56);
    resizeImageFile(file, "img/productSingle_middle", session.getServletContext(),217,190);
  }

  private void resizeImageFile(File srcFile, String imageFolderPath, ServletContext context, int horizon, int vertical) {
    File imageFolder = new File(context.getRealPath(imageFolderPath));
    if (!imageFolder.getParentFile().exists()) {
      imageFolder.getParentFile().mkdirs();
    }
    File file = new File(imageFolder, srcFile.getName());
    ImageUtil.resizeImage(srcFile, horizon, vertical, file);
  }

  private void detailImageProcess(HttpSession session, MultipartFile image, int id) throws IOException {
    File imageFolder = new File(session.getServletContext().getRealPath("img/productSingle"));
    ImageUtil.imageFileProcess(imageFolder, image, id);
  }

  private void deleteImageFile(String imageFolderPath, int id, HttpSession session) {
    File imageFolder = new File(session.getServletContext().getRealPath(imageFolderPath));
    File file = new File(imageFolder,id+".jpg");
    file.delete();
  }
}
