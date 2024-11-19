import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.util.Objects;

public class reviewView extends JPanel
{

    private final JPanel headerPanel;
    private final JLabel PageTitle;
    private final JLabel introduction;
    private final JLabel details;
    private JComboBox<String> brandBox;

    //constructor
    reviewView(Font oswald, Font lato) {
        //-----------------------Set background color and preferred size and title------------------------------------------------
        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());  // Use BorderLayout to properly place the navigation bar at the bottom

        //create title
        PageTitle = new JLabel("Review Page");
        PageTitle.setFont(oswald.deriveFont(20f));
        PageTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //create top panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        //add them to panel
        headerPanel.add(PageTitle, BorderLayout.CENTER);

        //set panel to top of screen
        this.add(headerPanel, BorderLayout.NORTH);

        //--------------------------------------------------------------------------------------------------------------

        //-------------------CALL NAVIGATION BAR AND SET BOUNDS---------------------------------------------------------
        navigationBarView navigationBarView = new navigationBarView(oswald, lato);
        this.add(navigationBarView, BorderLayout.SOUTH);  // Place navigation bar at the bottom (SOUTH)
        // -------------------------------------------------------------------------------------------------------------

        //-------------------CREATE items to go in panel----------------------------------------------------------------
        //create panel
        JPanel ReviewPanel = new JPanel();
        ReviewPanel.setLayout(new BoxLayout(ReviewPanel, BoxLayout.Y_AXIS));
        ReviewPanel.setBackground(new Color(235, 219, 195));

       // Create and add the introduction at the top
        introduction = new JLabel("<html><div style='text-align: center;'>"
                                      + "<br> Welcome to the Review Page");
        introduction.setFont(oswald.deriveFont(30f));
        introduction.setForeground(new Color(0, 99, 73));
        introduction.setAlignmentX(Component.CENTER_ALIGNMENT); //new
        ReviewPanel.add(introduction);

        ReviewPanel.add(Box.createVerticalStrut(20));

        //page details
        details = new JLabel("<html><div style='text-align: center;'>"
                + "<br> Discover insights into the environmental sustainability of <br>"
                + "popular brands. Each brand is rated on a scale from <br>"
                + "1 to 5 stars, with 5 representing eco-friendly <br>"
                + "practices and 1 indicating poor environmental performance."
                + "</div></html>");
        details.setFont(oswald.deriveFont(15f));
        details.setAlignmentX(Component.CENTER_ALIGNMENT);
        ReviewPanel.add(details);

        ReviewPanel.add(Box.createVerticalStrut(20));

        //add image
        ImageIcon coverImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/reviewPage.png")));
        Image scaledFact = coverImg.getImage().getScaledInstance(300, 190, 4); // Resize image smoothly
        coverImg = new ImageIcon(scaledFact);  // Update fact with resized image
        JLabel imgDisplay = new JLabel(coverImg);
        imgDisplay.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        ReviewPanel.add(imgDisplay);

        ReviewPanel.add(Box.createVerticalStrut(20));

        //create pull down bar
        String[] brands = {"--Brand--", "Abercrombie & Fitch", "Adidas", "Alo Yoga",
                           "Artizia", "Brandy Melville", "Fashion Nova", "Forever 21",
                           "Francesca's", "Free People", "Guess", "H&M", "Hollister",
                           "Hot Topic", "Lululemon", "Motel Rocks", "Nasty Gal", "Nike",
                           "PacSun", "PrettyLittleThing", "Princess Polly", "Romwe", "Shein",
                           "Steve Madden", "Tilly's", "Uniqlo", "Urban Outfitters", "Zumiez"};
        brandBox = createStyledComboBox(brands, lato);
        brandBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        ReviewPanel.add(brandBox);

        ReviewPanel.add(Box.createVerticalStrut(20));
       //---------------------------------------------------------------------------------------------------------------

        //--------------------------Create scroll panel-----------------------------------------------------------------
        // Wrap the ReviewPanel in a scroll pane
        JScrollPane scrollPane = new JScrollPane(ReviewPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Change scroll bar appearance
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                return new JButton() {  // Invisible decrease button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation)
            {
                return new JButton() {  // Invisible increase button
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = new Color(192, 168, 144); // Color of the scroll thumb
                this.trackColor = new Color(235, 219, 195); // Color of the scroll track (matching panel background)
            }
        });
        // Add the scrollable items to the CENTER of the layout
        this.add(scrollPane, BorderLayout.CENTER);

        //--------------------------------------------------------------------------------------------------------------
    }

    //function for pull down menu design
    private JComboBox<String> createStyledComboBox(String[] options, Font font)
    {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(font.deriveFont(Font.PLAIN, 14));
        comboBox.setBackground(new Color(235, 219, 195));

        // Lock the size to prevent layout shifts
        comboBox.setPreferredSize(new Dimension(300, 30)); // Set a fixed preferred size
        comboBox.setMaximumSize(new Dimension(300, 30)); // Set a fixed maximum size
        comboBox.setMinimumSize(new Dimension(300, 30)); // Set a fixed minimum size

        return comboBox;
    }
}
