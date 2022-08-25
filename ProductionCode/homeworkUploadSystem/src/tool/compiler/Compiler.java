package tool.compiler;

import java.io.IOException;

public interface Compiler {
	Process compile(Runtime rt) throws IOException;
}
