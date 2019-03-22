package Health_System_Monitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;

public class Form_GUI {
    public static JFrame mainFrame;
    private static JPanel titlepanel = new JPanel();
    private static JButton editbutton = new JButton("edit");
    private static JPanel editingpanel = new JPanel();
    private static JScrollPane scrollpanel = new JScrollPane();
    private static JPanel contentpanel = new JPanel();
    private static Vector<JButton> editButtonz = new Vector<JButton>();

    private static Vector<FormElement> formElements = new Vector<FormElement>();

    // convenience vectors to quickly access editable form elements
    private static Vector<JPanel> formPanels = new Vector<JPanel>();
    private static Vector<JTextField> formLabels = new Vector<JTextField>();
    private static Vector<JComponent> formEntries = new Vector<JComponent>();

    private static boolean editMode = false;

    private static int formId; // form we're using
    private static int submissionId; // submission ID for the current answer set (outside Edit Mode)

    private static FormDao dao;

    public static void prepareFormGUI() {
        JLabel titlelabel = new JLabel("title ");
        titlepanel.add(titlelabel);
        titlepanel.add(editbutton);

        for (FormType ft : FormType.values()) {
            if (ft != FormType.FT_ERROR) {
                String var = ft.toString();
                JButton nButton = new JButton(var);
                editButtonz.add(nButton);
                editingpanel.add(nButton);
                nButton.setEnabled(false);
                nButton.setVisible(false);
                nButton.setActionCommand(var);
                nButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FormType f = FormType.fromString(e.getActionCommand().toLowerCase());
                        insertItem(f);
                    }
                });
            }
        }

        JToggleButton editModeButton = new JToggleButton("Edit Mode", false);
        editModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleEditMode();
            }
        });
        editingpanel.add(editModeButton);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(titlepanel, BorderLayout.NORTH);
        mainFrame.add(editingpanel, BorderLayout.EAST);
        mainFrame.add(scrollpanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        contentpanel.setLayout(new FlowLayout());

        scrollpanel.add(contentpanel);

        dao = FormJDBC.getDAO();
    }

    public static void toggleEditMode() {
        editMode = !editMode;

        // flip the edit mode state of our panels
        for (int i = 0; i < formPanels.size(); i++) {
            // if we're not in edit mode, the label is read-only
            formLabels.get(i).setEnabled(editMode);
            // if the entryElement is a single thing (like a text box) then if we're not in edit mode it's editable
            JComponent entryElement = formEntries.get(i);
            entryElement.setEnabled(!editMode);
            // now check for children (eg, radio buttons)
            for (Component c : entryElement.getComponents()) {
                c.setEnabled(!editMode);
            }
        }

        // and make the edit buttonz materialise
        for (JButton button : editButtonz) {
            button.setVisible(editMode);
            button.setEnabled(editMode);
        }
    }


    public static void insertItem(FormType f) {
        // we now know the Type of our new form, so create an edit-mode version of that form element

        int newID = formElements.size();
        FormElement newElement = new FormElement();
        formElements.add(newElement);

        addQuestionToFormDao(newID);

        JPanel newPanel = createPanel(f);
        contentpanel.add(newPanel);
    }

    /// ---------------------------------------------------------------------------------

    private static void updateFormDaoForElement(int elementId)
    {
        // we just updated an Question element, so we need to update the corresponding part of the Question table
        FormElement fe = formElements.get(elementId);
        dao.updateQuestion(fe.question_id, fe.type, fe.label);
    }

    private static void addQuestionToFormDao(int elementId)
    {
        // we just added the question, so it needs to be added to the DAO
        FormElement fe = formElements.get(elementId);
        int id = dao.addQuestion(formId, fe.type, fe.label);
        fe.question_id = id;
    }

    private static void removeQuestionFromFormDao(int elementId)
    {
        // we just removed a question, so it needs to be removed from the DAO
        FormElement fe = formElements.get(elementId);
        dao.removeQuestion(fe.question_id);
    }

    private static void newSubmissionToFormDao()
    {
        // create new submission and populate the table
        submissionId = dao.addSubmission(formId, Main_GUI.getCurrentUser().getUserId());

        // add default answers for all fields
        for(FormElement fe : formElements)
        {
             fe.value = fe.default_value;
        }
    }

    private static void updateSubmissionAnswer(FormElement answer)
    {
        dao.updateAnswer(answer.question_id, submissionId, answer.value);
    }

    /// --------------------------------------------------------------------------------

    private static void updateElementLabel(int elementId, String labelText)
    {
        FormElement fe = formElements.get(elementId);
        fe.label = labelText;
        updateFormDaoForElement(elementId);
    }

    private static void updateElementControl(int elementId, Object value)
    {
        // this can only happen when we're not in Edit Mode, which means we have a Submission ID
        FormElement fe = formElements.get(elementId);
        fe.value = value;

    }

    /**
     * Create panel in form, initially in edit mode
     * @param f FormType of the panel to create
     * @return the new JPanel
     */
    private static JPanel createPanel(FormType f)
    {
        JPanel newPanel = new JPanel();

        // label is actually a text field; we turn off editable once we're done with Edit Mode
        JTextField labelField = new JTextField("Name");
        labelField.setEnabled(true);
        labelField.setVisible(true);
        int newIndex = formLabels.size();
        labelField.setActionCommand(String.valueOf(newIndex));
        labelField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigInteger indexB = new BigInteger(e.getActionCommand());
                int index = indexB.intValue();
                String text = formLabels.get(index).getText();
                updateElementLabel(index,text);
            }
        });
        newPanel.add(labelField);

        formLabels.add(labelField);

        // now add the appropriate
        switch (f)
        {
            case FT_BOOLEAN: {
                // 2 strong button group, mutually exclusive
                ButtonGroup boolGroup = new ButtonGroup();
                JRadioButton yesButton = new JRadioButton("Yes");
                JRadioButton noButton = new JRadioButton("No");
                boolGroup.add(yesButton);
                boolGroup.add(noButton);

                // we add our buttons to a JPanel so we can enable/disable them together
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(yesButton);
                buttonPanel.add(noButton);

                // buttons are visible but disabled in Edit Mode
                yesButton.setEnabled(false);
                noButton.setEnabled(false);
                yesButton.setVisible(true);
                noButton.setVisible(false);

                newPanel.add(buttonPanel);

                formEntries.add(buttonPanel);
            }
            break;
            case FT_INT: {
                // numerical input, only allow ints
                JTextField valueField = new JTextField();
                InputVerifier veri = new InputVerifier() {
                    @Override
                    public boolean verify(JComponent input) {
                        // try to create a BigDecimal with our text input
                        BigDecimal number;
                        try {
                            number = new BigDecimal(((JTextField) input).getText());
                        }
                        catch (NumberFormatException nfe)
                        {
                            // not a number, so reject
                            return false;
                        }

                        // if we got a number, use the intValueExact property of BigDecimal to test for an exact int
                        try {
                            int result = number.intValueExact();
                            return true;
                        }
                        catch (ArithmeticException e)
                        {
                            return false;
                        }

                    }
                };
                valueField.setInputVerifier(veri);

                // starts out as disabled but visible
                valueField.setEnabled(false);
                valueField.setVisible(true);

                newPanel.add(valueField);
                formEntries.add(valueField);
            }
            break;
            case FT_FLOAT: {
                // numerical input, only allow floats
                JTextField valueField = new JTextField();
                InputVerifier veri = new InputVerifier() {
                    @Override
                    public boolean verify(JComponent input) {
                        // try to create a BigDecimal with our text input
                        BigDecimal number;
                        try {
                            number = new BigDecimal(((JTextField) input).getText());
                        }
                        catch (NumberFormatException nfe)
                        {
                            // not a number, so reject
                            return false;
                        }

                        // if we got to this point, we're an acceptable decimal value, so we are OK
                        return true;

                    }
                };
                valueField.setInputVerifier(veri);

                // starts out as disabled but visible
                valueField.setEnabled(false);
                valueField.setVisible(true);

                newPanel.add(valueField);
                formEntries.add(valueField);
            }
            break;
            case FT_STRING: {
                // string input
                JTextField valueField = new JTextField();
                // no input verifier

                // starts out as disabled but visible
                valueField.setEnabled(false);
                valueField.setVisible(true);

                newPanel.add(valueField);
                formEntries.add(valueField);
            }
            break;
        }

        newPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return newPanel;
    }
}
