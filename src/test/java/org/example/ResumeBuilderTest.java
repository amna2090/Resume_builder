package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class ResumeBuilderTest {


    @Test
    public void testSaveResumeValidInput() {
            ResumeBuilder builder = new ResumeBuilder();

            // Simulate filling the fields with valid input
            builder.nameField.setText("John Doe");
            builder.emailField.setText("john.doe@example.com");
            builder.phoneField.setText("123-456-7890");
            builder.addressField.setText("123 Main St");
            builder.collegeField.setText("State University");
            builder.qualificationATitleField.setText("B.Sc. Computer Science");
            builder.qualificationBTitleField.setText("M.Sc. Software Engineering");
            builder.experienceDropdown.setSelectedItem("3-5 years");
            builder.skill1Field.setText("Java");
            builder.skill2Field.setText("Python");
            builder.skill3Field.setText("SQL");
            builder.skill4Field.setText("Problem-Solving");

            // Simulate the saveResume() method and ensure no exception is thrown
            assertDoesNotThrow(() -> {
                Method method = ResumeBuilder.class.getDeclaredMethod("saveResume");
                method.setAccessible(true); // Allow access to private methods
                method.invoke(builder); // Invoke the saveResume method
            });
    }

    @Test
    public void testSaveResumeEmptyName() {
        ResumeBuilder builder = new ResumeBuilder();
        builder.nameField.setText("");
        builder.emailField.setText("john.doe@example.com");
        builder.phoneField.setText("123-456-7890");
        builder.addressField.setText("123 Main St");
        builder.collegeField.setText("State University");
        builder.qualificationATitleField.setText("B.Sc. Computer Science");
        builder.qualificationBTitleField.setText("M.Sc. Software Engineering");
        builder.experienceDropdown.setSelectedItem("3-5 years");
        builder.skill1Field.setText("Java");
        builder.skill2Field.setText("Python");
        builder.skill3Field.setText("SQL");
        builder.skill4Field.setText("Problem-Solving");


        // Check for validation error dialog popup (using a mock framework could be better)
        assertThrows(Exception.class, builder::saveResume);
    }

}