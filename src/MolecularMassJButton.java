import java.awt.Font;

import javax.swing.JButton;

///////////////////////////////////////////////////////////////////////////////
// Class derived from `JButton'. The font size can be specified while calling
// the constructor. The default font size is less than that of `JButton'.
///////////////////////////////////////////////////////////////////////////////
public class MolecularMassJButton extends JButton
{
    ///////////////////////////////////////////////////////////////////////////
    // Constructor.
    ///////////////////////////////////////////////////////////////////////////
    MolecularMassJButton(String text)
    {
        super(text);
        this.setFont(new Font("DejaVu Sans", Font.PLAIN, 8));
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructor.
    ///////////////////////////////////////////////////////////////////////////
    MolecularMassJButton(String text, int fontSize)
    {
        super(text);
        this.setFont(new Font("DejaVu Sans", Font.PLAIN, fontSize));
    }
}

