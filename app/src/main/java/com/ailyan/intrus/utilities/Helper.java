package com.ailyan.intrus.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

public class Helper {
    public static int getRandomNumber(int size) {
        return new Random().nextInt(size);
    }
    public static byte[] getBytes(Context context, Uri imageUri) throws IOException {
        try {
            Bitmap bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            ByteBuffer byteBuffer = ByteBuffer.allocate(bmp.getByteCount());
            bmp.copyPixelsToBuffer(byteBuffer);
            byteBuffer.rewind();
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
