package com.how2java.tmall.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/2 0:01
 **/
@Getter @Setter
public class UploadFile {
  private MultipartFile filepath;
}
