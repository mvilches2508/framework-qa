package Util;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;


public class CustomOutputStream extends OutputStream {
	private JTextArea textArea;

	public CustomOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		// reenvio del flujo de datos
        textArea.append(String.valueOf((char)b));
        // mantener el scroll  abajo
        textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}