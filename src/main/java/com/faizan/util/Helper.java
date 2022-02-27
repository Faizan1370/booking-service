package com.faizan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import com.faizan.dto.ItemDTO;
import com.faizan.entity.Item;
import com.faizan.exception.BadRequestException;

public class Helper {

    public final static String UPLOAD_DIR = "D:\\Spring-Boot\\booking-service\\src\\main\\resources\\static\\image";
    // public final static String UPLOAD_DIR = new ClassPathResource("static").getFile().;

    public static void isValidRequest(ItemDTO dto) {
        if (isEmptyOrNull(dto.getItemName())) {
            throw new BadRequestException(" Item Name can't be empty or null");
        } else if (isEmptyOrNull(dto.getItemType())) {
            throw new BadRequestException("item Type can't be null or empty");
        } else if (dto.getItemPrice() == 0 || dto.getItemPrice() == null) {
            throw new BadRequestException("item Type can't be null or zero");
        } else if (dto.getQuantity() < 1) {
            throw new BadRequestException("Item quantity must be more than or one");
        }

    }

    public static boolean isEmptyOrNull(String str) {
        return (Objects.isNull(str)) ? true : str.isEmpty() ? true : str.trim().isEmpty() ? true : false;
    }

    public static boolean uploadfile(MultipartFile file) {
        boolean flag = false;

        try {
            final InputStream inputStream = file.getInputStream();
            final byte data[] = new byte[inputStream.available()];
            inputStream.read(data);

            final FileOutputStream fileOutputStream = new FileOutputStream(UPLOAD_DIR + File.separator + file.getOriginalFilename());
            fileOutputStream.write(data);
            fileOutputStream.flush();
            fileOutputStream.close();
            // new way
            // Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            flag = true;

        } catch (final Exception e) {
            e.printStackTrace();
        }

        return flag;

    }

    public static boolean isQuantityAvailable(Item item) {
        // final Long available = item.getQuantity() - item.getSoldQty();
        if (item.getAvailableQty() >= 1) {
            return true;
        } else {
            return false;
        }

    }

}
