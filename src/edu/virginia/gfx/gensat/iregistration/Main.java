package edu.virginia.gfx.gensat.iregistration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JApplet {
	private static final long serialVersionUID = 1L;
	private Editor editor;

	protected void initEditor() {
		// float[] points = new float[] { 0, 0, 0, 1, 1, 1, 1, 0 };
		// int[] triangles = new int[] { 0, 1, 2, 0, 2, 3 };

		Warp warp = new Warp(10, 10);

		BufferedImage imgWarp = null;
		BufferedImage imgTarget = null;
		try {
			// test image
			// imgWarp = ImageIO.read(new URL(
			// "http://colorvisiontesting.com/plate%20with%205.jpg"));
			// bean
			// imgWarp = ImageIO .read(new URL(
			// "http://cdn6.fotosearch.com/bthumb/FDS/FDS106/redkid4.jpg"));
			imgTarget = ImageIO.read(getClass().getResourceAsStream(
					"/brain.jpg"));
			// imgTarget = ImageIO .read(new URL(
			// "http://www.vnvlvokc.com/ow_userfiles/plugins/shoppro/images/product_1.jpg"));
			// imgWarp = ImageIO.read(new URL(
			// "http://www.gensat.org/atlas/ADULT_ATLAS_07.jpg"));
			imgWarp = ImageIO.read(getClass()
					.getResource("/ADULT_ATLAS_07.jpg"));
			// imgTarget = ImageIO
			// .read(getClass().getResourceAsStream("/brain2.jpg"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			editor = new Editor(warp, imgWarp, imgTarget);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		System.out.println("initializing... ");
		getContentPane().setLayout(new BorderLayout(0, 0));

		initEditor();

		final JSlider alphaSlider = new JSlider();
		alphaSlider.setMaximum(255);
		alphaSlider.setMinimum(0);
		alphaSlider.setValue(128);
		alphaSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				editor.setAlpha(alphaSlider.getValue());
			}
		});

		// FIXME These are terrible UI labels
		final JRadioButton editWarpRadioButton = new JRadioButton("Edit Warp");
		editWarpRadioButton.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (editWarpRadioButton.isSelected()) {
					editor.setEditorModeWarp();
				}
			}
		});
		final JRadioButton editAffineRadioButton = new JRadioButton(
				"Edit Affine");
		editAffineRadioButton.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (editAffineRadioButton.isSelected()) {
					editor.setEditorModeAffine();
				}
			}
		});
		final ButtonGroup editButtonGroup = new ButtonGroup();
		editButtonGroup.add(editWarpRadioButton);
		editButtonGroup.add(editAffineRadioButton);
		final JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.add(editAffineRadioButton);
		topPanel.add(editWarpRadioButton);

		// start with the affine button selected
		editAffineRadioButton.setSelected(true);
		editor.setEditorModeAffine();
		editWarpRadioButton.setSelected(false);

		final JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.submit();
			}
		});

		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setMinimumSize(new Dimension(20, 0));
		// FIXME separator causes strange layout behavior
		// topPanel.add(sep);
		topPanel.add(new JLabel("Transparency: "));
		topPanel.add(alphaSlider);
		topPanel.add(submitButton);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(topPanel);
		getContentPane().add(editor);
	}

	public void start() {
		System.out.println("starting... ");
	}

	@Override
	public void update(Graphics g) {
		super.update(g);
	}

	public void stop() {
		System.out.println("stopping... ");
	}

	public void destroy() {
		System.out.println("destroying...");
	}
}
