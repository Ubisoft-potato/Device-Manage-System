package org.cust.devicemanagesystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileInputStream;

/**
 * @ClassName Downloadcontroller @Description TODO @Author long @Date 2020/5/26 9:01 @Version 1.0
 */
@RestController
@Slf4j
public class DownloadController {

  @GetMapping(path = "file")
  public ResponseEntity<StreamingResponseBody> fileDownload() {
    StreamingResponseBody streamBody =
        outputStream -> {
          log.info("current thread: {}", Thread.currentThread().getName());
          FileInputStream fileInputStream = new FileInputStream("path");
          int length;
          byte[] bytes = new byte[4096];
          while ((length = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
          }
          fileInputStream.close();
          outputStream.close();
        };

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=xxx.pdf")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(streamBody);
  }
}
