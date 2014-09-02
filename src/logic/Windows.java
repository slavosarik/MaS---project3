package logic;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

public class Windows {

	private JFrame frame;
	private JButton btntart;
	private JTextArea textArea;
	private JLabel rozmerX;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Windows window = new Windows();
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
	public Windows() {
		initialize();

		textField_1.setText("3");
		textField_2.setText("3");
		textField_3.setText("3, 0, 2, 6, 5, 1, 4, 7, 8");
		textField_4.setText("5, 6, 1, 4, 8, 0, 3, 2, 7");

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame = new JFrame(
				"Hlavolam 15 - Modelovanie a Simulácia: Slavomír Šárik");
		frame.setBounds(100, 100, 1700, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 1664, 739);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		btntart = new JButton("\u0160tart ");
		btntart.setBounds(645, 7, 89, 23);
		panel.add(btntart);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 1644, 655);
		panel.add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.PLAIN, 16));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		scrollPane.setViewportView(textArea);

		rozmerX = new JLabel("RozmerX:");
		rozmerX.setBounds(10, 11, 66, 14);
		panel.add(rozmerX);

		textField_1 = new JTextField();
		textField_1.setBounds(94, 8, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel rozmerY = new JLabel("RozmerY:");
		rozmerY.setBounds(10, 48, 86, 14);
		panel.add(rozmerY);

		textField_2 = new JTextField();
		textField_2.setBounds(94, 42, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JLabel zacStav = new JLabel("Za\u010Diato\u010Dn\u00FD stav: ");
		zacStav.setBounds(202, 14, 142, 14);
		panel.add(zacStav);

		textField_3 = new JTextField();
		textField_3.setBounds(293, 8, 314, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);

		JLabel konStav = new JLabel("Kone\u010Dn\u00FD stav:");
		konStav.setBounds(201, 48, 89, 14);
		panel.add(konStav);

		textField_4 = new JTextField();
		textField_4.setBounds(293, 45, 314, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);

		btntart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				textArea.setText("");

				int puzzleSizeX = Integer.parseInt(textField_1.getText());
				int puzzleSizeY = Integer.parseInt(textField_2.getText());

				String[] input1 = textField_3.getText().replace(" ", "")
						.split(",");
				String[] input2 = textField_4.getText().replace(" ", "")
						.split(",");

				Integer[] zacStav = new Integer[input1.length];
				Integer[] konStav = new Integer[input2.length];

				for (int i = 0; i < input1.length; i++) {
					System.out.println(i);
					zacStav[i] = Integer.parseInt(input1[i]);
					konStav[i] = Integer.parseInt(input2[i]);
				}

				GreedyPrehladavanie greedy = new GreedyPrehladavanie(textArea);
				HeuristikaInterface vzdialenosti = new HeuristikaVzdialenosti();
				HeuristikaInterface pocetPolicok = new HeuristikaPocetNespravnychPolicok();

				textArea.append("Greedy algoritmus použitím heuristiky Manhattanskych vzdialenosti:\n");
				greedy.hladajCestu(zacStav, konStav, vzdialenosti, puzzleSizeX,
						puzzleSizeY);
				textArea.append("\nGreedy algoritmus použitím heuristiky poctu policok:\n");
				greedy.hladajCestu(zacStav, konStav, pocetPolicok, puzzleSizeX,
						puzzleSizeY);

				SirkaPrehladavanie sirka = new SirkaPrehladavanie(textArea);
				textArea.append("\nAlgoritmus prehladavanie do sirky:\n");
				sirka.hladajCestu(zacStav, konStav, puzzleSizeX, puzzleSizeY);

				RandomPrehladavanie randomPrehladavanie = new RandomPrehladavanie(
						textArea);
				textArea.append("\nAlgoritmus nahodnych krokov:\n");
				randomPrehladavanie.hladajCestu(zacStav, konStav, puzzleSizeX,
						puzzleSizeY);

			}
		});

	}

	public JButton getBtnstart() {
		return btntart;
	}

	public JTextArea getTextArea() {
		return textArea;
	}
}
