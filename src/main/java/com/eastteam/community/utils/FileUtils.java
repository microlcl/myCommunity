package com.eastteam.community.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * I/O utilities.
 * 
 * @author Lichlei
 */
public class FileUtils extends org.apache.commons.io.FileUtils
{
	private static final String PREFIX_FILE = "file:";

	private static final String PREFIX_CLASSPATH = "classpath:";

	/**
	 * read image file quietly.<br>
	 * read failed return null
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage readImageFile(String filename) throws IOException
	{
		return ImageIO.read(new File(filename));
	}

	/**
	 * @param packageResource
	 * @return
	 * @throws IOException
	 */
	public static StringBuffer readPackageResource(String packageResource) throws IOException
	{
		return readPackageResource(packageResource, null);
	}

	/**
	 * @param packageResource
	 * @param loader
	 * @return
	 * @throws IOException
	 */
	public static StringBuffer readPackageResource(String packageResource, ClassLoader loader) throws IOException
	{
		return readPackageResource(packageResource,loader,null);
	}

	/**
	 * @param packageResource
	 * @param loader
	 * @param encoding
	 *            the encoding of the resource
	 * @return
	 * @throws IOException
	 * @see
	 */
	public static StringBuffer readPackageResource(String packageResource, ClassLoader loader, String encoding)
		throws IOException
	{
		if (packageResource == null)
			return null;
		
		InputStream is = getPackageResourceStream(packageResource, loader);

		if (null == is)
			throw new IOException("load package resource failed. resource file: " + packageResource);

		StringBuffer sb = new StringBuffer(IOUtils.toString(is, encoding));
		
		return sb;
	}

	/**
	 * @param packageResource
	 * @return
	 * @throws IOException
	 */
	public static byte[] readPackageByteResource(String packageResource) throws IOException
	{
		return readPackageByteResource(packageResource, null);
	}

	/**
	 * @param packageResource
	 * @param loader
	 * @return
	 * @throws IOException
	 */
	public static byte[] readPackageByteResource(String packageResource, ClassLoader loader) throws IOException
	{
		if (packageResource == null)
			return null;
		InputStream is = null;
		try
		{
			// get data length
			is = getPackageResourceStream(packageResource, loader);
			int length = 0;
			while (is.read() != -1)
			{
				length++;
			}
			is.close();
			// read data
			byte[] data = new byte[length];
			is = getPackageResourceStream(packageResource, loader);
			is.read(data);
			is.close();
			return data;
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			if (is != null)
				is.close();
		}
	}

	/**
	 * get resource file path in package
	 * 
	 * @param packageResource
	 *            package resource relative path
	 * @return String package resource file path
	 */
	public static String getPackageResourcePath(String packageResource)
	{
		return getPackageResourcePath(packageResource, null);
	}

	/**
	 * get resource file path in package
	 * 
	 * @param packageResource
	 * @param loader
	 * @return
	 */
	public static String getPackageResourcePath(String packageResource, ClassLoader loader)
	{
		URL url = getPackageResourceUrl(packageResource, loader);
		if (url != null)
			return url.getFile().replaceAll("%20", " ");
		else
			return null;
	}

	/**
	 * get resource file path in package
	 * 
	 * @param packageResource
	 *            package resource relative path
	 * @return String package resource file path
	 */
	public static URL getPackageResourceUrl(String packageResource)
	{
		return getPackageResourceUrl(packageResource, null);
	}

	/**
	 * get resource file path in package
	 * 
	 * @param packageResource
	 * @param loader
	 * @return
	 */
	public static URL getPackageResourceUrl(String packageResource, ClassLoader loader)
	{
		if (packageResource == null)
			return null;
		if (packageResource.startsWith("\\") || packageResource.startsWith("/"))
			packageResource = packageResource.substring(1);
		if (packageResource.endsWith("\\") || packageResource.endsWith("/"))
			packageResource = packageResource.substring(0, packageResource.length() - 1);
		if (loader == null)
			loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(packageResource);
		if (url == null)
		{
			loader = FileUtils.class.getClassLoader();
			url = loader.getResource(packageResource);
		}
		return url;
	}

	/**
	 * get input stream in package
	 * 
	 * @param packageResource
	 * @return
	 */
	public static InputStream getPackageResourceStream(String packageResource)
	{
		return getPackageResourceStream(packageResource, null);
	}

	/**
	 * get input stream in package
	 * 
	 * @param packageResource
	 * @param loader
	 * @return
	 */
	public static InputStream getPackageResourceStream(String packageResource, ClassLoader loader)
	{
		if (packageResource == null)
			return null;
		if (packageResource.startsWith("\\") || packageResource.startsWith("/"))
			packageResource = packageResource.substring(1);
		if (packageResource.endsWith("\\") || packageResource.endsWith("/"))
			packageResource = packageResource.substring(0, packageResource.length() - 1);
		if (loader == null)
			loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream(packageResource);
		if (is == null)
		{
			loader = FileUtils.class.getClassLoader();
			is = loader.getResourceAsStream(packageResource);
		}
		return is;
	}

	/**
	 * get package resources names
	 * 
	 * @param classLoader
	 * @param packageName
	 * @return
	 * @throws IOException
	 * @see
	 */
	public static List<String> getPackageResoruceNames(ClassLoader classLoader, String packageName) throws IOException
	{
		return getPackageResoruceNames(classLoader, packageName, null);
	}

	public static List<String> getPackageResoruceNames(ClassLoader classLoader, String packageName, String extension)
		throws IOException
	{
		List<String> resourceNames = new ArrayList<String>();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		while (resources.hasMoreElements())
		{
			URL resource = resources.nextElement();
			if (resource.getProtocol().equalsIgnoreCase("FILE"))
			{
				resourceNames.addAll(getDirectoryResoruceNames(packageName, URLDecoder.decode(resource.getFile(), "UTF-8"), extension));
			}
			else if (resource.getProtocol().equalsIgnoreCase("JAR"))
			{
				resourceNames.addAll(getJarResoruceNames(packageName, resource, extension));
			}
		}

		return resourceNames;
	}

	/**
	 * get jar resources names
	 * 
	 * @param packageName
	 * @param resource
	 * @return
	 * @throws IOException
	 * @see
	 */
	public static List getJarResoruceNames(String packageName, URL resource) throws IOException
	{
		return getJarResoruceNames(packageName, resource, null);
	}

	public static List getJarResoruceNames(String packageName, URL resource, String extension) throws IOException
	{
		JarURLConnection conn = (JarURLConnection) resource.openConnection();
		JarFile jarFile = conn.getJarFile();

		return getJarResoruceNames(packageName, jarFile, extension);
	}

	/**
	 * get jar resources names
	 * 
	 * @param packageName
	 * @param file
	 * @return
	 * @throws IOException
	 * @see
	 */
	public static List getJarResoruceNames(String packageName, String file) throws IOException
	{
		return getJarResoruceNames(packageName, file, null);
	}

	public static List getJarResoruceNames(String packageName, String file, String extension) throws IOException
	{
		return getJarResoruceNames(packageName, new JarFile(file), extension);
	}

	/**
	 * get jar resources names
	 * 
	 * @param packageName
	 * @param jarFile
	 * @return
	 * @throws IOException
	 * @see
	 */
	public static List getJarResoruceNames(String packageName, JarFile jarFile) throws IOException
	{
		return getJarResoruceNames(packageName, jarFile, null);
	}

	public static List getJarResoruceNames(String packageName, JarFile jarFile, String extension) throws IOException
	{
		if (!StringUtils.isEmpty(extension) && !extension.startsWith("."))
			extension = "." + extension;

		List resourceNames = new ArrayList();

		Enumeration<JarEntry> entries = jarFile.entries();
		String packagePath = packageName.replace('.', '/');

		while (entries.hasMoreElements())
		{
			JarEntry entry = entries.nextElement();
			if ((entry.getName().startsWith(packagePath) || entry.getName()
					.startsWith("WEB-INF/classes/" + packagePath)))
			{
				if (StringUtils.isEmpty(extension) || entry.getName().endsWith(extension))
				{
					String className = entry.getName();
					resourceNames.add(entry.getName());
				}
			}
		}

		return resourceNames;
	}

	/**
	 * get directory Resource names
	 * 
	 * @param packageName
	 * @param fullPath
	 * @return
	 * @throws IOException
	 * @see
	 */
	public static List getDirectoryResoruceNames(String packageName, String fullPath) throws IOException
	{
		return getDirectoryResoruceNames(packageName, fullPath, null);
	}

	public static List getDirectoryResoruceNames(String packageName, String fullPath, String extension)
		throws IOException
	{
		List sourceNames = new ArrayList();
		getDirectoryResoruceNames(sourceNames, packageName, fullPath, extension);

		return sourceNames;
	}

	private static void getDirectoryResoruceNames(List sourceNames, String packageName, String fullPath,
													String extension) throws IOException
	{
		if (!StringUtils.isEmpty(extension) && !extension.startsWith("."))
			extension = "." + extension;

		packageName = packageName.replace('.', '/');
		if (!packageName.endsWith("/"))
			packageName += "/";
		
		File directory = new File(fullPath);
		if (!directory.isDirectory())
			throw new IOException("Invalid directory " + directory.getAbsolutePath());

		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				getDirectoryResoruceNames(packageName  + file.getName(), file.getAbsolutePath());
			}
			else
			{
				if (StringUtils.isEmpty(extension) || file.getName().endsWith(extension))
				{
					String className = packageName+ file.getName();
					sourceNames.add(className);
				}
			}
		}
	}

	/**
	 * Read file as string content from orginPath, example:
	 * FileUtils.resolveFile("classpath:co/service/chain/velocity.vm") or
	 * FileUtils.resolveFile("file:co/service/chain/velocity.vm") or
	 * FileUtils.resolveFile("file:${app_path}/co/service/chain/velocity.vm")
	 * 
	 * @param orginPath
	 *            file path, compatibility for "class:" and "file:" prefix
	 * @return file content string
	 * @throws IOException
	 */
	public static String resolveFile(String orginPath) throws IOException
	{
		if (StringUtils.isBlank(orginPath))
		{
			throw new IllegalArgumentException("invalide path:" + orginPath);
		}

		String path = orginPath;
		String content = null;

		if (orginPath.startsWith(PREFIX_FILE))
		{
			path = orginPath.substring(PREFIX_FILE.length());
			int beginIndex = path.indexOf("${");
			if (-1 != beginIndex)
			{
				int eIndex = path.indexOf("}");
				if (-1 == eIndex)
				{
					throw new IllegalArgumentException("invalide path:" + orginPath);
				}
				String var = path.substring(beginIndex + 2, eIndex);
				var = System.getProperty(var);
				if (StringUtils.isBlank(var))
				{
					throw new IllegalArgumentException("can't resolve the variable,invalide path:" + orginPath);
				}
				path = var + path.substring(eIndex + 1);
			}
			content = readFileToString(new File(path));

			return content;
		}

		if (orginPath.startsWith(PREFIX_CLASSPATH))
		{
			path = orginPath.substring(PREFIX_CLASSPATH.length());
		}

		content = readPackageResource(path).toString();

		return content;
	}

	/**
	 * Return the extension portion of the file's name .
	 * 
	 * @see #getExtension
	 */
	public static String getExtension(File f)
	{
		return (f != null) ? getExtension(f.getName()) : "";
	}

	public static String getExtension(String filename)
	{
		return getExtension(filename, "");
	}

	public static String getExtension(String filename, String defExt)
	{
		if ((filename != null) && (filename.length() > 0))
		{
			int i = filename.lastIndexOf('.');

			if ((i > -1) && (i < (filename.length() - 1)))
			{
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

}
