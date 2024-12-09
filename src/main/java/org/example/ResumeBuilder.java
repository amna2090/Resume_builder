package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

public class ResumeBuilder extends JFrame {
    // Input fields and components for the GUI
    JTextField nameField;
    JTextField emailField;
    JTextField phoneField;
    JTextField addressField;
    JTextField collegeField;
    JTextField qualificationATitleField;
    JTextField qualificationBTitleField;
    JComboBox<String> experienceDropdown;
    JTextField skill1Field;
    JTextField skill2Field;
    JTextField skill3Field;
    JTextField skill4Field;
    private JLabel imageLabel;
    private JButton uploadImageButton;
    private ImageIcon selectedImage; // To hold the selected image

    public ResumeBuilder() {
        setTitle("Resume Builder");
        setSize(1000, 400); // Adjusted the size for a better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the main panel with padding
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding: top, left, bottom, right

        // Personal Information Panel (Left side)
        JPanel personalInfoPanel = new JPanel();
        personalInfoPanel.setLayout(new BoxLayout(personalInfoPanel, BoxLayout.Y_AXIS));
        personalInfoPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));

        // Create components for personal information
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();

        // Add labels and text fields to the personal information panel
        personalInfoPanel.add(createLabeledPanel("Name:", nameField));
        personalInfoPanel.add(createLabeledPanel("Email:", emailField));
        personalInfoPanel.add(createLabeledPanel("Phone:", phoneField));
        personalInfoPanel.add(createLabeledPanel("Address:", addressField));

        // Image selection
        imageLabel = new JLabel("Profile Image: None");
        uploadImageButton = new JButton("Select Image");
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "gif"));
                if (fileChooser.showOpenDialog(ResumeBuilder.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    // Store the file path to a variable to use later
                    selectedImage = new ImageIcon(file.getPath());
                    // Display the scaled image in the JLabel
                    java.awt.Image scaledImage = selectedImage.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLabel.setText(""); // Clear the default text
                }
            }
        });
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);
        imagePanel.add(uploadImageButton);
        personalInfoPanel.add(imagePanel);

        // Qualifications Panel (Right side - top section)
        JPanel qualificationsPanel = new JPanel();
        qualificationsPanel.setLayout(new BoxLayout(qualificationsPanel, BoxLayout.Y_AXIS));
        qualificationsPanel.setBorder(BorderFactory.createTitledBorder("Qualifications"));

        collegeField = new JTextField();
        qualificationATitleField = new JTextField();
        qualificationBTitleField = new JTextField();

        // Add labels and text fields for qualifications
        qualificationsPanel.add(createLabeledPanel("College/University:", collegeField));
        qualificationsPanel.add(createLabeledPanel("Title of Qualification A:", qualificationATitleField));
        qualificationsPanel.add(createLabeledPanel("Title of Qualification B:", qualificationBTitleField));

        // Experience Panel (Middle section)
        JPanel experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.Y_AXIS));
        experiencePanel.setBorder(BorderFactory.createTitledBorder("Experience"));

        String[] experienceOptions = {"<1 year", "2-3 years", "3-5 years", "No experience"};
        experienceDropdown = new JComboBox<>(experienceOptions);
        experiencePanel.add(createLabeledPanel("Experience:", experienceDropdown));

        // Updating the Skills Panel
        JPanel skillsPanel = new JPanel();
        skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));
        skillsPanel.setBorder(BorderFactory.createTitledBorder("Skills"));

        skill1Field = new JTextField();
        skill2Field = new JTextField();
        skill3Field = new JTextField();
        skill4Field = new JTextField();

        // Add labels and text fields for skills
        skillsPanel.add(createLabeledPanel("Skill 1:", skill1Field));
        skillsPanel.add(createLabeledPanel("Skill 2:", skill2Field));
        skillsPanel.add(createLabeledPanel("Skill 3:", skill3Field));
        skillsPanel.add(createLabeledPanel("Skill 4:", skill4Field));

        // Add all sections to the main panel
        mainPanel.add(personalInfoPanel);
        mainPanel.add(qualificationsPanel);
        mainPanel.add(experiencePanel);
        mainPanel.add(skillsPanel);

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Add a save button at the bottom
        JButton saveButton = new JButton("Save Resume");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Center the window
        setLocationRelativeTo(null); // Centers the frame on the screen

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveResume();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Helper method to create a labeled panel for input fields
    private <T extends JComponent> JPanel createLabeledPanel(String labelText, T inputComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        if (inputComponent instanceof JTextField) {
            inputComponent.setPreferredSize(new Dimension(200, 25)); // Set preferred size
        }
        panel.add(inputComponent);
        return panel;
    }

    // Helper method to create a labeled panel for dropdowns
    private JPanel createLabeledPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(comboBox);
        return panel;
    }
    void saveResume() throws Exception {
        // Retrieve text from input fields
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String college = collegeField.getText().trim();
        String qualificationATitle = qualificationATitleField.getText().trim();
        String qualificationBTitle = qualificationBTitleField.getText().trim();
        String experience = (String) experienceDropdown.getSelectedItem();
        String skill1 = skill1Field.getText().trim();
        String skill2 = skill2Field.getText().trim();
        String skill3 = skill3Field.getText().trim();
        String skill4 = skill4Field.getText().trim();

        // Validate inputs
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() ||
                college.isEmpty() || qualificationATitle.isEmpty() || qualificationBTitle.isEmpty() ||
                experience == null || skill1.isEmpty() || skill2.isEmpty() || skill3.isEmpty() || skill4.isEmpty()) {
            throw new Exception("All fields must be filled out before saving.");
        }

        try {
            // Define the path for the PDF
            // Add a line that spans the width of the page

            String pdfFilePath = "resume.pdf";
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFilePath));
            Document document = new Document(pdfDocument);
            LineSeparator lineSeparator = new LineSeparator(new SolidLine());
            lineSeparator.setWidth(UnitValue.createPercentValue(100)); // Full width of the page
            PdfFont font = PdfFontFactory.createFont(); // This will use the default Helvetica font

            // Create a table with two columns for name and image
            Table table = new Table(12);
            table.setWidth(UnitValue.createPercentValue(100)); // Set table width to full page

            // Add the name to the first cell
            Cell nameCell = new Cell().add(new Paragraph(name).setFont(font).setFontSize(18));
            nameCell.setTextAlignment(TextAlignment.LEFT); // Ensure name aligns to the left
            nameCell.setBorder(Border.NO_BORDER); // Optional: Remove borders for a cleaner look
            table.addCell(nameCell);

            // Add the image to the second cell if it exists
            if (selectedImage != null && selectedImage.getImage() != null) {
                // Convert the selectedImage to a valid image path (or use a temporary path)
                String imagePath = selectedImage.getDescription(); // Assumes getDescription() holds the file path
                if (imagePath != null) {
                    // Create an Image object using the path
                    Image image = new Image(ImageDataFactory.create(imagePath));
                    image.scaleToFit(100, 100); // Scale the image to fit the cell
                    Cell imageCell = new Cell().add(image);
                    // Add the image to the second cell
                    imageCell.setBorder(Border.NO_BORDER); // Optional: Remove borders for a cleaner look
                    imageCell.setTextAlignment(TextAlignment.RIGHT); // Align the content to the right
                    table.addCell(imageCell);
                }
            }

            // Add the table to the document
            document.add(table);
            // ADD SEPARATE
            document.add(lineSeparator);
            document.add(new Paragraph("PERSONAL INFORMATION").setBold().setMarginTop(30));
            document.add(lineSeparator);
            document.add(new Paragraph("Email: " + email)).setFont(font).setFontSize(14);
            document.add(new Paragraph("Phone: " + phone)).setFont(font).setFontSize(14);
            document.add(new Paragraph("Address: " + address)).setFont(font).setFontSize(14);
            document.add(new Paragraph("QUALIFICATION").setBold().setMarginTop(20));
            document.add(lineSeparator);
            document.add(new Paragraph(college));
            document.add(new Paragraph(qualificationATitle));
            document.add(new Paragraph(qualificationBTitle));
            document.add(new Paragraph("\nEXPERIENCE:").setBold().setMarginTop(20));
            document.add(lineSeparator);
            document.add(new Paragraph(experience));
            document.add(new Paragraph("\nSKILLS:").setBold().setMarginTop(20));
            document.add(lineSeparator);
            String[] skills = {skill1 , skill2 , skill3 , skill4};
            for (String skill : skills) {
                document.add(new Paragraph(skill));
            }



            // Close the document
            document.close();

            JOptionPane.showMessageDialog(this, "Resume saved as PDF successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving resume: " + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResumeBuilder builder = new ResumeBuilder();
            builder.setVisible(true);
        });
    }
}
