package ru.maxizenit.footballleaguemanager.gui.dialog.entitydialog;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.maxizenit.footballleaguemanager.entity.Position;
import ru.maxizenit.footballleaguemanager.exception.InvalidFieldException;

/**
 * Диалог для позиций.
 */
public class PositionDialog extends EntityDialog<Position> {

  private JPanel mainPanel;

  private JButton buttonOK;

  private JButton buttonCancel;

  /**
   * Поля порядкового номера.
   */
  private JTextField indexNumberField;

  /**
   * Поле кода.
   */
  private JTextField codeField;

  /**
   * Поле названия.
   */
  private JTextField nameField;

  public PositionDialog(JFrame parent, SaveFunction<Position> saveFunction,
      RefreshGUIFunction refreshGUIFunction, Position entity) {
    super(parent, saveFunction, refreshGUIFunction, entity);
    init(mainPanel, buttonOK, buttonCancel);
    pack();
    setVisible(true);
  }

  @Override
  protected void fillFields(Position entity) {
    indexNumberField.setText(entity.getIndexNumber().toString());
    codeField.setText(entity.getCode());
    nameField.setText(entity.getName());
  }

  @Override
  protected boolean isFieldsValid() throws InvalidFieldException {
    try {
      Integer.parseInt(indexNumberField.getText());
    } catch (NumberFormatException e) {
      List<String> invalidFields = new ArrayList<>();
      invalidFields.add("порядковый номер");
      throw new InvalidFieldException(invalidFields);
    }

    return true;
  }

  @Override
  protected Position createNewEntity() {
    return new Position();
  }

  @Override
  protected void fillEntityFromFields(Position entity) {
    entity.setIndexNumber(Integer.parseInt(indexNumberField.getText()));
    entity.setCode(codeField.getText());
    entity.setName(nameField.getText());
  }

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayoutManager(4, 2, new Insets(10, 10, 10, 10), -1, -1));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
    mainPanel.add(panel1, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    buttonOK = new JButton();
    buttonOK.setText("OK");
    panel1.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    buttonCancel = new JButton();
    buttonCancel.setText("Cancel");
    panel1.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label1 = new JLabel();
    label1.setText("Порядковый номер");
    mainPanel.add(label1,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label2 = new JLabel();
    label2.setText("Код");
    mainPanel.add(label2,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label3 = new JLabel();
    label3.setText("Название");
    mainPanel.add(label3,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    indexNumberField = new JTextField();
    mainPanel.add(indexNumberField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    codeField = new JTextField();
    mainPanel.add(codeField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    nameField = new JTextField();
    mainPanel.add(nameField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return mainPanel;
  }

}
