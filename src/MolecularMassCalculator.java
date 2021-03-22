import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

///////////////////////////////////////////////////////////////////////////////
// Class derived from `JFrame'. Displays a window in which the constitution of
// a compound can be entered. The molecular mass of the compound can be
// calculated. I chose to use `javax.swing' instead of `javafx' because the
// latter won't get installed on Debian.
///////////////////////////////////////////////////////////////////////////////
public class MolecularMassCalculator extends JFrame
{
    private int defaultWidth = 100;
    private int defaultHeight = 100;
    private int borderWidth = 24;
    private int padWidth = borderWidth / 2;
    private int fontSize = 16;

    private MolecularMassJLabel lblMass;

    private JTable table;
    private MolecularMassTableModel model;

    private HashMap<String, Double> masses;

    ///////////////////////////////////////////////////////////////////////////
    // Constructor.
    ///////////////////////////////////////////////////////////////////////////
    MolecularMassCalculator(String name)
    {
        super(name);

        ConstructMassMap();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icons/MolecularMassCalculatorIcon.png").getImage());
        setLocation(100, 100);
        setResizable(false);
        setSize(defaultWidth, defaultHeight);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(padWidth, padWidth, padWidth, padWidth);

        // Panel to use as the main container of the frame.
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));
        panel.setLayout(new GridBagLayout());
        setContentPane(panel);

        // Header label.
        gbc.gridy = 0;
        MolecularMassJLabel lblTitle = new MolecularMassJLabel(name, fontSize * 2);
        add(lblTitle, gbc);

        // Model for the below table.
        MolecularMassTableModel model = new MolecularMassTableModel(new String[] {"Symbol of Element", "Number of Atoms"}, 0);
        model.addTableModelListener(new TableModelListener()
        {
            public void tableChanged(TableModelEvent e)
            {
                CalculateMass();
            }
        });
        this.model = model;

        // Table to be placed in the scrollable section of the frame.
        JTable table = new JTable();
        table.setFillsViewportHeight(true);
        table.setFont(new Font("DejaVu Sans", Font.PLAIN, fontSize));
        table.getTableHeader().setFont(new Font("DejaVu Sans", Font.BOLD, fontSize));
        table.setPreferredScrollableViewportSize(new Dimension(150, 150));
        table.setModel(model);
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer ()
        {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String symbol = (String)value;
                if(symbol != null && !symbol.equals("") && !masses.containsKey(symbol))
                {
                    component.setBackground(Color.YELLOW);
                }
                else
                {
                    component.setBackground(Color.WHITE);
                }

                return component;
            }
        });
        this.table = table;

        // Scrollable section of the frame.
        gbc.gridy = 1;
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, gbc);

        // Button to add rows.
        gbc.gridy = 2;
        MolecularMassJButton btnAddTableRow = new MolecularMassJButton("Add New Row", fontSize);
        btnAddTableRow.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddTableRow();
            }
        });
        add(btnAddTableRow, gbc);

        // Button to delete rows.
        gbc.gridy = 3;
        MolecularMassJButton btnDeleteTableRow = new MolecularMassJButton("Delete Selected Row", fontSize);
        btnDeleteTableRow.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DeleteTableRow();
            }
        });
        add(btnDeleteTableRow, gbc);

        // Label to display calculated mass.
        gbc.gridy = 4;
        MolecularMassJLabel lblMass = new MolecularMassJLabel("Mass will be displayed here.", fontSize);
        add(lblMass, gbc);
        this.lblMass = lblMass;

        pack();
        setVisible(true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Create a map containing the symbols and atomic masses of the elements.
    // I chose not to use the double-brace notation because there might be
    // significant performance impacts, considering that there are so many
    // elements. The masses were obtained from `molmass', a Python package.
    ///////////////////////////////////////////////////////////////////////////
    private void ConstructMassMap()
    {
        masses = new HashMap<String, Double>();
        masses.put("H",   1.007941);
        masses.put("He",  4.002602);
        masses.put("Li",  6.94);
        masses.put("Be",  9.0121831);
        masses.put("B",  10.811);
        masses.put("C",  12.01074);
        masses.put("N",  14.006703);
        masses.put("O",  15.999405);
        masses.put("F",  18.998403163);
        masses.put("Ne", 20.1797);
        masses.put("Na", 22.98976928);
        masses.put("Mg", 24.3051);
        masses.put("Al", 26.9815385);
        masses.put("Si", 28.0855);
        masses.put("P",  30.973761998);
        masses.put("S",  32.0648);
        masses.put("Cl", 35.4529);
        masses.put("Ar", 39.948);
        masses.put("K",  39.0983);
        masses.put("Ca", 40.078);
        masses.put("Sc", 44.955908);
        masses.put("Ti", 47.867);
        masses.put("V",  50.9415);
        masses.put("Cr", 51.9961);
        masses.put("Mn", 54.938044);
        masses.put("Fe", 55.845);
        masses.put("Co", 58.933194);
        masses.put("Ni", 58.6934);
        masses.put("Cu", 63.546);
        masses.put("Zn", 65.38);
        masses.put("Ga", 69.723);
        masses.put("Ge", 72.63);
        masses.put("As", 74.921595);
        masses.put("Se", 78.971);
        masses.put("Br", 79.9035);
        masses.put("Kr", 83.798);
        masses.put("Rb", 85.4678);
        masses.put("Sr", 87.62);
        masses.put("Y",  88.90584);
        masses.put("Zr", 91.224);
        masses.put("Nb", 92.90637);
        masses.put("Mo", 95.95);
        masses.put("Tc", 97.9072);
        masses.put("Ru", 101.07);
        masses.put("Rh", 102.9055);
        masses.put("Pd", 106.42);
        masses.put("Ag", 107.8682);
        masses.put("Cd", 112.414);
        masses.put("In", 114.818);
        masses.put("Sn", 118.71);
        masses.put("Sb", 121.76);
        masses.put("Te", 127.6);
        masses.put("I",  126.90447);
        masses.put("Xe", 131.293);
        masses.put("Cs", 132.90545196);
        masses.put("Ba", 137.327);
        masses.put("La", 138.90547);
        masses.put("Ce", 140.116);
        masses.put("Pr", 140.90766);
        masses.put("Nd", 144.242);
        masses.put("Pm", 144.9128);
        masses.put("Sm", 150.36);
        masses.put("Eu", 151.964);
        masses.put("Gd", 157.25);
        masses.put("Tb", 158.92535);
        masses.put("Dy", 162.5);
        masses.put("Ho", 164.93033);
        masses.put("Er", 167.259);
        masses.put("Tm", 168.93422);
        masses.put("Yb", 173.054);
        masses.put("Lu", 174.9668);
        masses.put("Hf", 178.49);
        masses.put("Ta", 180.94788);
        masses.put("W",  183.84);
        masses.put("Re", 186.207);
        masses.put("Os", 190.23);
        masses.put("Ir", 192.217);
        masses.put("Pt", 195.084);
        masses.put("Au", 196.966569);
        masses.put("Hg", 200.592);
        masses.put("Tl", 204.3834);
        masses.put("Pb", 207.2);
        masses.put("Bi", 208.9804);
        masses.put("Po", 208.9824);
        masses.put("At", 209.9871);
        masses.put("Rn", 222.0176);
        masses.put("Fr", 223.0197);
        masses.put("Ra", 226.0254);
        masses.put("Ac", 227.0278);
        masses.put("Th", 232.0377);
        masses.put("Pa", 231.03588);
        masses.put("U",  238.02891);
        masses.put("Np", 237.0482);
        masses.put("Pu", 244.0642);
        masses.put("Am", 243.0614);
        masses.put("Cm", 247.0704);
        masses.put("Bk", 247.0703);
        masses.put("Cf", 251.0796);
        masses.put("Es", 252.083);
        masses.put("Fm", 257.0951);
        masses.put("Md", 258.0984);
        masses.put("No", 259.101);
        masses.put("Lr", 262.1096);
        masses.put("Rf", 267.1218);
        masses.put("Db", 268.1257);
        masses.put("Sg", 271.1339);
        masses.put("Bh", 272.1383);
        masses.put("Hs", 270.1343);
        masses.put("Mt", 276.1516);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Add an empty row to the table.
    ///////////////////////////////////////////////////////////////////////////
    private void AddTableRow()
    {
        model.addRow(new Object[] {null, null});
    }

    ///////////////////////////////////////////////////////////////////////////
    // Delete the selected row of the table.
    ///////////////////////////////////////////////////////////////////////////
    private void DeleteTableRow()
    {
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1)
        {
            return;
        }

        model.removeRow(selectedRow);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Calculate the molecular mass using the values entered in the table.
    ///////////////////////////////////////////////////////////////////////////
    private void CalculateMass()
    {
        double mass = 0;
        for(int i = 0; i < table.getRowCount(); ++i)
        {
            Object symbol = table.getValueAt(i, 0);
            Object number = table.getValueAt(i, 1);
            if(symbol == null || number == null)
            {
                continue;
            }

            String s = (String)symbol;
            if(!masses.containsKey(symbol))
            {
                continue;
            }

            int n = (int)number;
            if(n < 0)
            {
                continue;
            }

            mass += masses.get(s) * n;
        }

        lblMass.setText(mass + " u");
    }

    ///////////////////////////////////////////////////////////////////////////
    // Main function.
    ///////////////////////////////////////////////////////////////////////////
    public static void main(String[] args)
    {
        MolecularMassCalculator calculator = new MolecularMassCalculator("Molecular Mass Calculator");
    }
}

