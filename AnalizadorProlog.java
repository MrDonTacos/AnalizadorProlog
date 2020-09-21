package analizadorProlog;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;

public class AnalizadorProlog {

	private JFrame frame;
	private String oracion = "";
	
	//Se obtiene el último elemento que no contenga una palabra
	private int findLastNonWordChar (String text, int index)
	{
		while(--index >= 0)
		{
			if(String.valueOf(text.charAt(index)).matches("\\W"))
			{
				break;
			}
		}
		return index;
	}
	
	//Se obtiene obtiene la primera no letra del programa, se utiliza la expresión \\W para ello
	// \\W = Nonword 
	private int findFirstNonWordChar (String text, int index)
	{
		while(index < text.length())
		{
			if(String.valueOf(text.charAt(index)).matches("\\W"))
			{
				break;
			}
			index ++;
		}
		return index;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnalizadorProlog window = new AnalizadorProlog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AnalizadorProlog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(301, 12, 312, 218);
		frame.getContentPane().add(scrollPane);
		
				JTextArea textArea = new JTextArea();
				scrollPane.setViewportView(textArea);
		StyleContext context = new StyleContext();
		DefaultStyledDocument document = new DefaultStyledDocument()
		{
			public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
			{
				super.insertString(offset, str, a);
				
				String text = getText(0, getLength());
				int before = findLastNonWordChar(text, offset);
				if(before <0) before = 0;
				int after = findFirstNonWordChar(text, offset + str.length());
				int wordL = before;
				int wordR = before;
				
				while (wordR <= after) {
					
					if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W"))
					{
						if(text.substring(wordL, wordR).matches("(\\W)*((_)|[A-Z])([a-zA-Z])*"))
						{
							textArea.append("\n" +text.substring(wordL, wordR) + ": Se ha validado el token de una variable");
						}
						else if(text.substring(wordL, wordR).matches("[\\:-]"))
						{
							textArea.append("\n" +text.substring(wordL, wordR) + ": Se ha validado un token condicional");
						}
						else if(text.substring(wordL, wordR).matches("[^('\n')][\\S][+-]?[0-9]*([.]([0]*[1-9]*|[1-9]*[0-9]*))*"))
						{
							textArea.append("\n" + text.substring(wordL, wordR) + ": Se ha validado el token de un número");
						}
						else if(text.substring(wordL, wordR).matches("(Functor)[(]([a-zA-Z]+[,0-9]*)*[)]"))
						{
							textArea.append("\n" +text.substring(wordL, wordR) + ": Se ha validado el token de una función");
						}
						else if(text.substring(wordL, wordR).matches("(((<)|(>)))"))
						{
							textArea.append("\n" +text.substring(wordL, wordR) + ": Se ha validado un token de comparación");
						}
						else if(text.substring(wordL, wordR).matches("[\\.]"))
						{
							textArea.append("\n" +text.substring(wordL, wordR) + "Se ha validado un token\nde termino de instrucción");
						}
						wordL = wordR;
					}
					wordR++;
				}
			}
		};
		JTextPane textPane = new JTextPane(document);
	
		/*textPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Se verifica el input entrante no sea un Enter 
				if(e.getKeyChar() != ' ' && e.getKeyChar() != '\n')
					//Se verifica si el input entrante es un backspace
					if(e.getKeyChar() == '\b')
					{	//En caso de serlo, elimina la última letra de la oración					
						if(oracion.length()>0)
						{
							StringBuffer sb = new StringBuffer(oracion); 
							sb.deleteCharAt(sb.length()-1);
							oracion = sb.toString();
						}
					}
					//En caso de no ser un backspace se concatena el valor entrante al string
					else
					oracion += e.getKeyChar();
				//En caso de que la tecla sea un enter, se verifica todo lo que se almacenó en la oración
				else
				{
					if(oracion.matches("(\\W)*((_)|[A-Z])([a-zA-Z])*"))
					{
							textField.setText(oracion + " Se ha validado el token de una variable");
					}
					else if(oracion.matches("((\\:)(\\-))+"))
					{
						textField.setText(oracion + " Se ha validado un token condicional");
					}
					else if(oracion.matches("[^('\n')][+-]?[0-9]*([.]([0]*[1-9]*|[1-9]*[0-9]*))*"))
					{
						textField.setText(oracion + " Se ha validado el token de un número");
					}
					else if(oracion.matches("(Functor)[(]([a-zA-Z]+[,0-9]*)*[)]"))
					{
						textField.setText("Se ha validado el token de una función");
					}
					else if(oracion.matches("(((<)|(>)|(<=)|(>=)|(==)|(/=)))"))
					{
						textField.setText("Se ha validado un token de comparación");
					}
					else if(oracion.matches("[\\.]"))
					{
						textField.setText("Se ha validado un token de termino de instrucción");
					}
					else
					{
						textField.setText("No coincide con ninguna de los tokens existentes");
					}
					oracion = "";
			}
			
		}
		});*/
		
		textPane.setBounds(12, 12, 264, 218);
		frame.getContentPane().add(textPane);
		
	}
}
