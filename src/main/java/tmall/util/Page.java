package tmall.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.ref.ReferenceQueue;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/1 20:38
 **/
@Getter @Setter
public class Page {
  private int start = 0;
  private int count = 5;
  private int total;
  private String param;

  public int getLast() {
    int last;
    if (total % count == 0) {
      last = total - count;
    } else {
      last = total-total%count;
    }
    return last = last<0?0:last;
  }

  public boolean isHasPreviouse() {
    return start!=0;
  }

  public boolean isHasNext() {
    return start!=getLast();
  }

  public int getTotalPage() {
    int totalPage;
    if (total % count == 0) {
      totalPage = total / count;
    } else {
      totalPage = total/count+1;
    }
    return totalPage==0?1:totalPage;
  }
}
