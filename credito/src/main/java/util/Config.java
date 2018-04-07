package util;

public class Config {

	public static final String CHARSET = "UTF8";
	public static String db = "creditoTest";

	 public static String pathFile = "c://crd//credito//files//";
	 public static String pathFileResult ="C:\\Users\\LENOVO\\Documents\\Trabajo\\credito\\credito-web\\src\\result\\";
//	 public static String pathFileResult = "E:\\Santiago\\creativos-digitales\\credito\\credito-web\\src\\result\\";
	 public static String pathModuleFile = "c://crd//credito//cargar-csv.xq";
	 public static String pathModuleMatrix = "c://crd//credito//matrices.xq";
	 public static String pathDB = "C://Program Files (x86)//BaseX//data";
	 public static String pathConfig = "c:\\crd\\credito\\";

//	public static String pathFile = "/home/mayordomo/credito/files/";
//	public static String pathFileResult = "/var/www/html/credito/result/";
//	public static String pathModuleFile = "/home/mayordomo/credito/cargarcsv.xq";
//	public static String pathModuleMatrix = "/home/mayordomo/credito/matrices.xq";
//	public static String pathDB = "/home/mayordomo/basex/data/";
//	public static String pathConfig = "/home/mayordomo/credito/";

	public static String collection = "let $db := collection('" + db + "') ";
	public static String moduleFile = "import module namespace file = 'http://creativosdigitales.co/cartera/import' at '"
			+ pathModuleFile + "';";

	public static String moduleMatrix = "import module namespace matrix = 'http://creativosdigitales.co/cartera/import' at '"
			+ pathModuleMatrix + "';";

	public static String email = "noreply@creativosdigitales.co";
	public static String clave = "2kqscJGn0I+z";
	public static String host = "mail.creativosdigitales.co";

}
