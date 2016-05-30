package hotP2B.WageGainTools.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.kymjs.kjframe.KJBitmap;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;

public class ImageUtils 
{

    
    /** 请求相册 */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /** 请求相机 */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    /** 请求裁剪 */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;
    
	public static String getAbsolutePathFromNoStandardUri(Uri mUri) 
	{
        String filePath = null;

        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);

        String pre1 = "file://" + "/sdcard" + File.separator;
        String pre2 = "file://" + "/mnt/sdcard" + File.separator;

        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre2.length());
        }
        return filePath;
	}
	@SuppressWarnings("deprecation")
    public static String getAbsoluteImagePath(Activity context, Uri uri) 
	{
        String imagePath = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.managedQuery(uri, proj, // Which columns to
                                                        // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) 
        {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }

        return imagePath;
    }
	
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) 
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
     }
	
	 public static String bmpToBase64(final Bitmap bmp, final boolean needRecycle) 
	 {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 10, output);
		if (needRecycle) 
		{
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Base64.encodeToString(result,Base64.DEFAULT);
     }
	
	 @SuppressWarnings("deprecation")
     public static Bitmap loadImgThumbnail(Activity context, String imgName,int kind) 
	 {
        Bitmap bitmap = null;

        String[] proj = { MediaStore.Images.Media._ID,MediaStore.Images.Media.DISPLAY_NAME };

        Cursor cursor = context.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
                MediaStore.Images.Media.DISPLAY_NAME + "='" + imgName + "'",
                null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) 
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
           // bitmap = MethodsCompat.getThumbnail(crThumb, cursor.getInt(0),
            //        kind, options);
        }
        return bitmap;
    }

	public static Bitmap loadImgThumbnail(String filePath, int w, int h) 
	{
	    Bitmap bitmap = getBitmapByPath(filePath);
	    return zoomBitmap(bitmap, w, h);
	}
	
	 public static Bitmap getBitmapByPath(String filePath) 
	 {
	    return getBitmapByPath(filePath, null);
	 }

     public static Bitmap getBitmapByPath(String filePath,
            BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }
     
     public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) 
     {
         Bitmap newbmp = null;
         if (bitmap != null) 
         {
             int width = bitmap.getWidth();
             int height = bitmap.getHeight();
             Matrix matrix = new Matrix();
             float scaleWidht = ((float) w / width);
             float scaleHeight = ((float) h / height);
             matrix.postScale(scaleWidht, scaleHeight);
             newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                     true);
         }
         return newbmp;
     }
     
 	public static void displayImage(ImageView imageView,String url,int defaultImage) 
 	{
       new KJBitmap().displayWithLoadBitmap(imageView,url,defaultImage);
 	}
     

}
