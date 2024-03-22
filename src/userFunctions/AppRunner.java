package userFunctions;

public class AppRunner {
	public static void main(String[] args) {
		UserController appCEO = new UserController();
		appCEO.startUserOperation();
		appCEO.shutDownOps();
	}
}
