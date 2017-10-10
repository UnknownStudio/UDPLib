package team.unstudio.udpl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtils {

	private ZipUtils(){}
	
	public static boolean zip(File zipFile, File... inputFiles) {
		try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))){
			for(File file:inputFiles){
				if(file.isDirectory())
					zip(out, file, "");
				else
					zip(out, file, file.getName());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			if (!base.isEmpty()) {
				out.putNextEntry(new ZipEntry(base + "/"));
				base = base + "/";
			}
			for (File f1:f.listFiles()) {
				zip(out, f1, base + f1.getName()); // 递归遍历子文件夹
			}
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
			try(FileInputStream in = new FileInputStream(f)){
				int b;
				while ((b = in.read()) != -1)
					out.write(b); // 将字节流写入当前zip目录
			}
		}
	}

}
