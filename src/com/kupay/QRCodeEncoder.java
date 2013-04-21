package com.kupay;
import android.graphics.Bitmap;
import android.os.Bundle;
import java.util.EnumMap;
import java.util.Map;
 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
 
public final class QRCodeEncoder {
    private static final int WHITE = 0xFFFFFF;
    private static final int BLACK = 0xC51E4F;
 
    private int dimension = Integer.MIN_VALUE;
    private String contents = null;
    private String displayContents = null;
    private String title = null;
    private BarcodeFormat format = null;
    private boolean encoded = false;
 
    public QRCodeEncoder(String data, int dimension) {
        this.dimension = dimension;
        encoded = encodeContents(data, null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString());
    }
 
    public String getContents() {
        return contents;
    }
 
    public String getDisplayContents() {
        return displayContents;
    }
 
    public String getTitle() {
        return title;
    }
 
    private boolean encodeContents(String data, Bundle bundle, String type, String formatString) {
        // Default to QR_CODE if no format given.
        format = null;
        if (formatString != null) {
            try {
                format = BarcodeFormat.valueOf(formatString);
            } catch (IllegalArgumentException iae) {
                // Ignore it then
            }
        }
        if (format == null || format == BarcodeFormat.QR_CODE) {
            this.format = BarcodeFormat.QR_CODE;
            encodeQRCodeContents(data, bundle, type);
        } else if (data != null && data.length() > 0) {
            contents = data;
            displayContents = data;
            title = "Text";
        }
        return contents != null && contents.length() > 0;
    }
 
    private void encodeQRCodeContents(String data, Bundle bundle, String type) {
        if (type.equals(Contents.Type.TEXT)) {
            if (data != null && data.length() > 0) {
                contents = data;
                displayContents = data;
                title = "Text";
            }
        } 
         
    }
 
    public Bitmap encodeAsBitmap() throws WriterException {
        if (!encoded) return null;
 
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(contents, format, dimension, dimension, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
 
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
 
    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) { return "UTF-8"; }
        }
        return null;
    }

}