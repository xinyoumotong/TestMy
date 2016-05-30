package hotP2B.WageGainTools.android.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import junit.framework.Assert;

public class ImageHelper 
{
	 
      
	  public static boolean saveImage(Bitmap bitmap,String path)
      {
    	  if (bitmap == null) return false;
    	  File file = new File(path);
    	  
	      try
	      {
    	     if(!file.exists())
    	     {
    	       file.createNewFile();
    	       FileOutputStream os = new FileOutputStream(file);
  	           bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
  	           os.flush();
  	           os.close();
    	     }  
	       
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	      if(file.exists())
	      {
	    	  return true;
	      }
	      else
	      {
	    	 return false;
	      }
      }
	  
	  public static boolean saveWebImage(String url,String path)
	  {
		  if (TextUtils.isEmpty(url)) return false;
		  File file = new File(path);
	      try
	 	  {
	    	  
	    	  if(!file.exists())
	    	  {
	    		  FileOutputStream os = new FileOutputStream(file);
		    	  InputStream is=new URL(url).openStream();
		    	  int bytesRead = 0;
		    	  byte[] buffer = new byte[8192];
		    	  while((bytesRead = is.read(buffer, 0, buffer.length)) != -1) 
		    	  {
		    	     os.write(buffer, 0, bytesRead);
		    	  }
		    	  os.flush();
		    	  os.close();
		    	  is.close();
	    	  }
	    	 
	 	   }
	 	   catch (Exception e)
	 	   {
	 	     e.printStackTrace();
	 	   }

	      if(file.exists())
	      {
	    	  return true;
	      }
	      else
	      {
	    	 return false;
	      }
		  
	  }
	  
	  private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
      public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) 
      {
			Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

			BitmapFactory.Options options = new BitmapFactory.Options();

			try {
				options.inJustDecodeBounds = true;
				Bitmap tmp = BitmapFactory.decodeFile(path, options);
				if (tmp != null) {
					tmp.recycle();
					tmp = null;
				}

				final double beY = options.outHeight * 1.0 / height;
				final double beX = options.outWidth * 1.0 / width;
				options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
				if (options.inSampleSize <= 1) {
					options.inSampleSize = 1;
				}

				// NOTE: out of memory error
				while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
					options.inSampleSize++;
				}

				int newHeight = height;
				int newWidth = width;
				if (crop) {
					if (beY > beX) {
						newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
					} else {
						newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
					}
				} else {
					if (beY < beX) {
						newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
					} else {
						newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
					}
				}

				options.inJustDecodeBounds = false;


				Bitmap bm = BitmapFactory.decodeFile(path, options);
				if (bm == null) {
					return null;
				}


				final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
				if (scale != null) {
					bm.recycle();
					bm = scale;
				}

				if (crop) 
				{
					final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
					if (cropped == null) {
						return bm;
					}

					bm.recycle();
					bm = cropped;
				}
				return bm;

			} catch (final OutOfMemoryError e) {
				options = null;
			}

			return null;
		}
      
      
         
		
}
