import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Objects;

public class reviewView extends JPanel {

    private final JPanel headerPanel;
    private final JLabel PageTitle;
    private final navigationBarView navigationBar;

    private final JLabel introduction;
    private final JLabel details;
    private  JLabel imgDisplay;
    private JLabel brandDisplay;

    private final JComboBox<String> brandBox;
    private final Font lato;

    private final JPanel mainContentPanel; // Main content panel for switching views

    // Constructor
    public reviewView(Font oswald, Font lato)
    {
        this.lato = lato;

        this.setBackground(new Color(235, 219, 195));
        this.setLayout(new BorderLayout());

        //------------------------------------------HEADER, TITLE, NAV BAR------------------------------------------------------
        // Create top panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(235, 219, 195));

        // Create title
        PageTitle = new JLabel("Review Page");
        PageTitle.setFont(oswald.deriveFont(20f));
        PageTitle.setHorizontalAlignment(SwingConstants.CENTER); // Center it
        headerPanel.add(PageTitle, BorderLayout.CENTER);

        this.add(headerPanel, BorderLayout.NORTH); // Place at the top

        // Add navigation bar
        navigationBar = new navigationBarView(oswald, lato);
        this.add(navigationBar, BorderLayout.SOUTH); // Place navigation bar at the bottom (SOUTH)

        //--------------------------------------Main panel info-----------------------------------------------------------------
        // Main content panel for the introduction and brand reviews
        mainContentPanel = new JPanel(null);
        mainContentPanel.setBackground(new Color(235, 219, 195));
        this.add(mainContentPanel, BorderLayout.CENTER);

        // Create and add the introduction at the top
        introduction = new JLabel("<html><div style='text-align: center;'>"
                + "<br>Welcome to the Review Page"
                + "</div></html>");
        introduction.setFont(oswald.deriveFont(30f));
        introduction.setForeground(new Color(0, 99, 73));
        introduction.setHorizontalAlignment(SwingConstants.CENTER); // Center text
        introduction.setBounds(-5, -10, 400, 100);
        mainContentPanel.add(introduction);

        // Page details
        details = new JLabel("<html><div style='text-align: center;'>"
                + "<br>Discover insights into the environmental sustainability of <br>"
                + "popular brands. Each brand is rated on a scale from <br>"
                + "1 to 5 stars, with 5 representing eco-friendly <br>"
                + "practices and 1 indicating poor environmental performance.<br><br>"
                + "Aditionally here are some factors we look at to determine rating: <br>"
                + "materials, transparency, environmnetal impact, packaging, carbon footprint, ect."
                + "</div></html>");
        details.setFont(oswald.deriveFont(15f));
        details.setHorizontalAlignment(SwingConstants.CENTER); // Center text
        details.setBounds(-5, 20, 400, 300);
        mainContentPanel.add(details);

        // Create pull-down menu
        String[] brands = {"--Brand--", "Abercrombie & Fitch", "Adidas", "Alo Yoga",
                           "American Eagle", "Artizia", "Athropologie", "Afends", "Brandy Melville", "CHNGE",
                           "Charlotte Russe", "Cotton On", "DK Active","Fashion Nova", "Forever 21", "Francesca's",
                           "Free People", "Girlfriend Collective", "GAP", "Guess", "H&M", "Hollister", "Hot Topic", "J.Crew",
                           "Levi Strauss & Co", "Lululemon", "Motel Rocks", "Nasty Gal", "Nordstrom", "Nike",
                           "Old Navy", "Organic Basics", "PacSun", "PrettyLittleThing", "Princess Polly", "Ralph Lauren",
                           "Romwe", "Shein", "Steve Madden", "Tilly's", "Tommy Hilfiger", "Tripulse", "Under Armour", "Uniqlo",
                           "Urban Outfitters", "Zara", "Zumiez"};
        brandBox = createStyledComboBox(brands, lato);
        brandBox.setBounds(70, 550, 250, 50);
        brandBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainContentPanel.add(brandBox);


            ImageIcon coverImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/susBrand.jpg")));
            Image scaledFact = coverImg.getImage().getScaledInstance(300, 250, 4); // Resize image smoothly
            coverImg = new ImageIcon(scaledFact); // Update fact with resized image
            imgDisplay = new JLabel(coverImg);
            imgDisplay.setBounds(40, 270, 300, 300);
            mainContentPanel.add(imgDisplay);

        //------------------------------------------ACTION LISTENER------------------------------------------------------------
        // Add action listener to the combo box
        brandBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String selectedBrand = (String) brandBox.getSelectedItem();

                if (selectedBrand != null && !selectedBrand.equals("--Brand--"))
                {
                    showBrandReview(selectedBrand, oswald);
                }
            }
        });
    }

    // Function to create a styled combo box
    private JComboBox<String> createStyledComboBox(String[] options, Font font)
    {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(font.deriveFont(Font.PLAIN, 14));
        comboBox.setBackground(new Color(235, 219, 195));
        return comboBox;
    }

    // Function to display the brand review panel
    private void showBrandReview(String brandName, Font oswald)
    {
        int startX = -9;

        mainContentPanel.removeAll(); // Clear the main content panel
        headerPanel.setVisible(false); // Hide header panel
        navigationBar.setVisible(false); // Hide navigation bar

        // Create brand review label
        JLabel brandLabel = new JLabel(brandName);
        brandLabel.setFont(oswald.deriveFont(30f));
        brandLabel.setHorizontalAlignment(SwingConstants.CENTER);
        brandLabel.setBounds(50, 50, 300, 50);
        mainContentPanel.add(brandLabel);


        if(brandName.equals("Abercrombie & Fitch"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>"
                                          +"While they do seem to be using some recycled materials<br>"
                                          +"with their clothing and packaging, they do not take action in<br>"
                                          +"eliminating hazardous chemicals in manufacturing.<br>"
                                          +"Additionally, the brand lacks transparency in its supply chain,<br>"
                                          +"failing to disclose detailed information about their labor practices<br>"
                                          +"or environmental impact beyond surface-level initiatives.<br>"
                                          +"They have yet to commit to significant measures that align<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 20, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/AF3.jpeg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(50, 300, 300, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 90, 90);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 90, 90);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



            if(brandName.equals("Adidas"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br>"
                                     +"While the brand has made some efforts towards sustainability,<br>"
                                     +"such as incorporating recycled plastics in their products<br>"
                                     +"and setting goals to reduce carbon emissions,<br>"
                                     +"there remain significant gaps in their practices.<br>"
                                     +"Adidas continues to rely on materials and processes<br>"
                                     +"that contribute to environmental degradation,<br>"
                                     +"and their progress towards eliminating hazardous chemicals<br>"
                                     +"in manufacturing has been slow. Furthermore, transparency in <br>"
                                     +"their supply chain is limited, with insufficient information<br>"
                                     +"provided on worker conditions and steps taken to ensure fair wages.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 50, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/adidas2.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Alo Yoga"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br><br>"
                                     +"The brand markets itself as eco-conscious, but its efforts toward<br>"
                                     +"sustainability and ethical practices fall short. While Alo Yoga uses some<br>"
                                     +"eco-friendly materials in its products, there is limited<br>"
                                     +"evidence of significant action to reduce carbon emissions or minimize<br>"
                                     +"environmental harm during production. The brand provides little transparency<br>"
                                     +"about its supply chain, offering minimal insight into the working<br>"
                                     +"conditions of its labor force or measures taken to ensure fairwages.<br>"
                                     +"Additionally, there is no clear commitment to eliminating hazardous<br>"
                                     +"chemicals or implementing circular fashion principles.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 20, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/alo4.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("American Eagle"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                      +"<br><br><br><br>"
                                      +"While the brand has initiated some sustainability efforts, such as<br>"
                                      +" incorporating recycled materials and setting targets to reduce<br>"
                                      +"greenhouse gas emissions, these measures are insufficient. There is no<br>"
                                      +"substantial evidence of progress toward these goals, and the brand<br>"
                                      +"lacks transparency regarding its supply chain practices. Notably,<br>"
                                      +"American Eagle has not ensured the payment of living wages to workers in<br>"
                                      +"its supply chain, and there is limited information on actions taken to<br>"
                                      +"protect biodiversity or reduce water usage. Additionally,<br>"
                                      +" the brand's animal welfare policies are minimal, with the use<br>"
                                      +"of materials like leather and exotic animal hair without<br>"
                                      +"clear sourcing information.<br>"
                                      + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/AE2.jpg.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Artizia"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>"
                                          +"While the brand has made some strides in sustainability, such as using<br>"
                                          +"lower-impact materials like organic cotton and certifying some<br>"
                                          +"of its wool and down products, these efforts are insufficient. Aritzia <br>"
                                          +"lacks comprehensive measures to reduce greenhouse gas emissions and <br>"
                                          +"has not set clear targets for emission reductions. The brand's supply<br>"
                                          +"chain transparency is limited, with no substantial evidence<br>"
                                          +"of ensuring living wages for workers or supporting diversity leather,<br>"
                                          +"silk, and inclusion. Additionally, Aritzia continues to use materials like<br>"
                                          +"and exotic animal hair without clear sourcing information,<br>"
                                          +"raising concerns about animal welfare.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/aritzia.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Athropologie"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>"
                                          +"The brand has made some efforts toward sustainability, such as using a<br>"
                                          +"small proportion of eco-friendly materials and incorporating renewable<br>"
                                          +"energy in its direct operations. However, these measures are<br>"
                                          +"insufficient. Anthropologie lacks comprehensive initiatives to reduce<br>"
                                          +"or eliminate hazardous chemicals and has not implemented water<br>"
                                          +"reduction strategies. The brand's supply chain transparency is limited,<br>"
                                          +"with no substantial evidence of ensuring living wages for workers<br>"
                                          +"or supporting diversity and inclusion. Additionally, Anthropologie<br>"
                                          +"continues to use materials like leather, wool, and exotic animal hair<br>"
                                          +"without clear sourcing information, raising<br>"
                                          +"concerns about animal welfare.<br>"
                                           + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/anthro.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Afends"))
        {
            JLabel brand= new JLabel("<html><div style='text-align: center;'>"
                                         +"<br><br><br><br>"
                                         +"The brand excels in sustainability by using eco-friendly materials<br>"
                                         +"like organic cotton and hemp, reducing chemical and water use.<br>"
                                         +"Afends also minimizes greenhouse gas emissions and uses compostable<br>"
                                         +"packaging. Labor practices are improving, with some supply chain<br>"
                                         +"certifications and worker rights policies, though living wages are<br>"
                                         +"not ensured. The brand avoids materials like leather, fur, and exotic<br>"
                                         +"animal skins while using Responsible Wool Standard-certified<br>"
                                         +" wool. Overall, Afends shows a strong commitment to sustainability<br>"
                                         +"with progress in ethical labor and animal welfare practices.<br>"
                                         + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/afends2.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 4; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Brandy Melville"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>"
                                          +"The brand has made significant strides in sustainability, incorporating <br>"
                                          +"eco-friendly materials such as organic cotton and recycled fabrics<br>"
                                          +"into their products. They have implemented measures to reduce <br>"
                                          +"carbon emissions and have increased transparency in their supply chain,<br>"
                                          +"ensuring fair labor practices and worker rights. Additionally,<br>"
                                          +"Brandy Melville has adopted animal-friendly policies, avoiding the use<br>"
                                          +"of fur, leather, and other animal-derived materials. Overall, the brand<br>"
                                          +"demonstrates a strong commitment to environmental responsibility<br>"
                                          +"and ethical production.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/Brandy.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 4; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("CHNGE"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>"
                                          +"The brand demonstrates exceptional commitment to sustainability<br>"
                                          +"by utilizing 100% organic materials, including GOTS-certified cotton,<br>"
                                          +"and ensuring climate-neutral production processes. CHNGE's<br>"
                                          +"manufacturing partners are Fairtrade certified, guaranteeing fair<br>"
                                          +"wages and safe working conditions. The brand also emphasizes transparency,<br>"
                                          +" providing detailed informationabout their supply chain and environmental<br>"
                                          +" impact. Additionally, CHNGE uses eco-friendly dyes and recycles<br>"
                                          +"95% of the water used during the dyeing process. Overall, CHNGE<br>"
                                          +"exemplifies outstanding dedication to environmental responsibility and ethical practices.<br>"

                + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/change.jpg.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 5; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Charlotte Russe"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>"
                                          +"The brand provides insufficient relevant information<br>"
                                          +"about how it reduces its impact on people, the planet, and animals.<br>"
                                          +"There is a lack of transparency regarding environmental policies,<br>"
                                          +"labor practices, and animal welfare standards.<br>"
                                          +"Without clear commitments or evidence of sustainable initiatives,<br>"
                                          +"Charlotte Russe's approach to corporate<br>"
                                          +"responsibility remains inadequate.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/char.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Cotton On"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>"
                                          +"While the brand has initiated some sustainability efforts, such as<br>"
                                          +"using lower-impact materials like recycled fabrics, these measures<br>"
                                          +"are insufficient. Cotton On follows a fast fashion model with rapidly<br>"
                                          +"changing trends, contributing to overconsumption and waste. There is<br>"
                                          +"no substantial evidence of meaningful action to reduce water usage<br>"
                                          +"or eliminate hazardous chemicals in manufacturing. The brand's supply<br>"
                                          +"chain transparency is limited, with little of it certified by crucial<br>"
                                          +"labor standards that ensure worker health, safety, and living wages.<br>"
                                          +"Additionally, Cotton On's animal welfare policies are inadequate,<br>"
                                          +"as it uses materials like leather and wool without clear sourcing<br>"
                                          +"information. verall, Cotton On's efforts toward sustainability and ethical<br>"
                                          +" practices are inadequate and lack the necessary depth and commitment.<br>"
                                           + "</div></html>");
            brand.setFont(oswald.deriveFont(12f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/cottonOn.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("DK Active"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand demonstrates exceptional commitment to sustainability<br>"
                                          +"by using a high proportion of lower-impact materials, including recycled<br>"
                                          +"fabrics. It employs low-impact materials that help limit chemicals, water,<br>"
                                          +"and wastewater in its supply chain, and offers clothing recycling<br>"
                                          +"to consumers to address end-of-life textile waste. dk active<br>"
                                          +"manufactures its products closer to home to reduce the climate<br>"
                                          +"impact of long-distance shipping and avoids plastic packaging.<br>"
                                          +"Regarding animal welfare, dk active states that its entire<br>"
                                          +"product range is vegan.<br>"
                                          +"Overall, dk active exemplifies outstanding dedication<br>"
                                          +"to environmental responsibility and ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/DKact.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 350);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 5; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 570, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Fashion Nova"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"with limited use of eco-friendly materials and no substantial initiatives<br>"
                                          +"to reduce carbon emissions or waste. There is a lack of <br>"
                                          +"transparency regarding labor practices, with no evidence of ensuring<br>"
                                          +"fair wages or safe working conditions throughout its supply chain.<br>"
                                          +"Additionally, Fashion Nova lacks clear policies on animal welfare,<br>"
                                          +"using materials like leather and wool without information<br>"
                                          +"on sourcing or ethical standards.<br>"
                                          +"Overall, Fashion Nova's approach to sustainability and ethical practices<br>"
                                          +"is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/fashonNova.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Forever 21"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br><br>The brand has made minimal efforts toward sustainability, using few <br>"
                                     +"lower-impact materials and following an unsustainable fast fashion model<br>"
                                     +"with quickly changing trends and regular new styles. There is no evidence<br>"
                                     +"of meaningful action to reduce climate impacts or minimize textile<br>"
                                     +"waste in its supply chain. Forever 21's labor practices are rated <br>"
                                     +"'very poor', with no evidence of ensuring living wages for workers<br>"
                                     +"or supporting diversity and inclusion. The brand uses materials like wool<br>"
                                     +"and exotic animal hair without clear policies to minimize<br>"
                                     +"animal suffering. Overall, Forever 21's approach to sustainability and<br>"
                                     +"ethical practices is inadequate and lacks meaningful commitment.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/forever21.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Francesca's"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br><br>The brand has made minimal efforts toward sustainability, with no<br>"
                                     +" evidence of using eco-friendly materials or taking meaningful action<br>"
                                     +"to reduce its climate impacts. It has not implemented measures to reduce<br>"
                                     +"hazardous chemicals or minimize packaging waste. Labor practices<br>"
                                     +"are also inadequate, with no certifications to ensure worker safety<br>"
                                     +"or fair wages, and no evidence that living wages are paid in its supply<br>"
                                     +"chain. Additionally, Francesca's lacks an animal welfare policy, using<br>"
                                     +"materials like leather and wool without traceability.<br>"
                                     +"Overall, Francesca's demonstrates insufficient commitment<br>"
                                     +"to sustainability and ethical practices.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/fran.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }



        if(brandName.equals("Free People"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br><br>The brand has made some efforts toward sustainability,<br>"
                                     +"such as using a small proportion of eco-friendly materials like recycled<br>"
                                     +"fabrics. However, these measures are insufficient. Free People<br>"
                                     +"follows an unsustainable fast fashion model with quickly changing<br>"
                                     +"trends and regular new styles. There is no evidence it has taken<br>"
                                     +"meaningful action to reduce or eliminate hazardous chemicals in its<br>"
                                     +"supply chain. Additionally, Free People uses materials like leather and wool<br>"
                                     +"without clear policies to minimize animal suffering.<br>"
                                     +"Overall, Free People's efforts toward sustainability and ethical practices<br>"
                                     +"are inadequate and lack the necessary depth and commitment.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/freePeople.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Girlfriend Collective"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br><br>The brand demonstrates strong sustainability efforts,<br>"
                                     +"using eco-friendly materials like recycled PET plastic and ECONYL.<br>"
                                     +"It employs low-impact dyes and treats wastewater responsibly. Girlfriend<br>"
                                     +"Collective minimizes waste through initiatives like 'ReGirlfriend'<br>"
                                     +"to promote a circular economy. Labor practices are commendable, with<br>"
                                     +"some parts of its supply chain certified by SA8000 and adherence<br>"
                                     +"to international labor standards. The brand traces its supply chain<br>"
                                     +"partially, covering final and some second production stages. Additionally,<br>"
                                     +"its products are cruelty-free and vegan, using no animal-derived<br>"
                                     +"materials. Overall, Girlfriend Collective is a solid choice<br>"
                                     +"for environmentally and ethically conscious consumers.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/gfCol.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 4; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("GAP"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br><br>The brand has made significant progress in sustainability,<br>"
                                     +"sourcing 98% of its cotton from more sustainable sources and allocating<br>"
                                     +"78% of its sourcing spend to green-rated factories. Gap has set<br>"
                                     +"science-based targets to reduce greenhouse gas emissions and received<br>"
                                     +"an A- rating for CDP Climate Change. The brand is committed to<br>"
                                     +"empowering women in its supply chain, with 91% of strategic factories<br>"
                                     +"having representative workplace committees. However, there is still<br>"
                                     +"room for improvement in ensuring living wages for all workers and<br>"
                                     +"further reducing environmental impact. Overall, Gap demonstrates<br>"
                                     +" a strong commitment to sustainability and ethical practices.<br>"
                                      + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/gap.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 4; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Guess"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made significant progress in sustainability,<br>"
                                          +"sourcing 98% of its cotton from more sustainable sources and<br>"
                                          +"allocating 78% of its sourcing spend to green-rated factories.<br>"
                                          +"Gap has set science-based targets to reduce greenhouse gas emissions<br>"
                                          +"and received an A- rating for CDP Climate Change. The brand is<br>"
                                          +"committed to empowering women in its supply chain, with 91% of<br>"
                                          +"strategic factories having representative workplace committees.<br>"
                                          +" However, there is still room for improvement in ensuring living<br>"
                                          +"wages for all workers and further reducing environmental impact.<br>"
                                          +"Overall, Gap demonstrates a strong commitment to sustainability and<br>"
                                          +"ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/guess.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }

        if(brandName.equals("H&M"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made some efforts toward sustainability,<br>"
                                          +"such as using lower-impact materials, including recycled fabrics.<br>"
                                          +"However, these measures are insufficient. H&M follows an<br>"
                                          +"unsustainable fast fashion model with quickly changing trends<br>"
                                          +"and regular new styles. There is no evidence it's taking meaningful<br>"
                                          +"action to reduce or eliminate hazardous chemicals in manufacturing.<br>"
                                          +"Additionally, H&M uses materials like leather and wool without clear<br>"
                                          +"policies to minimize animal suffering. Overall, H&M's efforts toward<br>"
                                          +"sustainability and ethical practices are inadequate and lack the <br>"
                                          +"necessary depth and commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/h&m3.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Hollister"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made some progress in sustainability,<br>"
                                          +"using lower-impact materials like recycled fabrics. It has also set<br>"
                                          +"targets to reduce greenhouse gas emissions and improve its<br>"
                                          +"environmental impact. However, Hollister's fast fashion model<br>"
                                          +"remains a concern, contributing to overconsumption and waste.<br>"
                                          +"There is limited transparency in its supply chain, with no evidence of<br>"
                                          +"ensuring payment of a living wage to workers. Additionally, Hollister<br>"
                                          +"lacks comprehensive animal welfare policies, using materials like<br>"
                                          +"leather and wool without clear sourcing standards. Overall, Hollister<br>"
                                          +"demonstrates moderate progress but still has room for improvement<br>"
                                          +"in sustainability and ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/holister.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 3; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Hot Topic"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                    +"<br><br><br><br>The brand provides insufficient relevant information<br>"
                    +"about how it reduces its impact on people, the planet, and animals.<br>"
                    +"There is no evidence of meaningful action to reduce or eliminate<br>"
                    +"hazardous chemicals in manufacturing.<br>"
                    +"Hot Topic's supply chain transparency is limited,<br>"
                    +"with no evidence of ensuring payment of a living wage to workers.<br>"
                    +"Additionally, the brand uses materials like leather and wool<br>"
                    +"without clear policies to minimize animal suffering.<br>"
                    +"Overall, Hot Topic's efforts toward sustainability and ethical practices<br>"
                    +"are inadequate and lack the necessary depth and commitment.<br>"
                    + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/hotTopic.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("J.Crew"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made some progress in sustainability,<br>"
                                          +"using lower-impact materials like recycled fabrics. It has set goals<br>"
                                          +"to source 100% of key fibers sustainably by 2025 and produce over 90%<br>"
                                          +"of its cashmere and chino collections in Fair Trade Certified <br>"
                                          +"facilities. However, J.Crew's fast fashion model contributes to<br>"
                                          +"overconsumption and waste. There is limited transparency in its<br>"
                                          +"supply chain, with no evidence of ensuring payment of a living<br>"
                                          +"wage to workers. Additionally, J.Crew uses materials like<br>"
                                          +"leather and wool without clear policies to minimize<br>"
                                          +"animal suffering. Overall, J.Crew demonstrates moderate progress<br>"
                                          +"but still has room for improvement in sustainability and ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/jcrew.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 3; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Levi Strauss & Co"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br>The brand has made significant progress in sustainability,<br>"
                                          +"focusing on three main pillars: climate, consumption, and community.<br>"
                                          +"It has set targets to reduce greenhouse gas emissions and is<br>"
                                          +"committed to sourcing 100% more sustainable cotton.<br>"
                                          +"Levi's has also implemented Water<Less® techniques, saving over 3<br>"
                                          +"billion liters of water in denim production. In terms of labor<br>"
                                          +"practices, the company promotes worker well-being through initiatives<br>"
                                          +"like the Worker Well-being program, aiming to improve the lives<br>"
                                          +"of apparel workers. Additionally, Levi's has a formal animal<br>"
                                          +"welfare policy and avoids the use of fur and exotic animal skins.<br>"
                                          +"Overall, Levi Strauss & Co. demonstrates a strong commitment<br>"
                                          +"to sustainability and ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 350);
            mainContentPanel.add(brand);

           ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/levi.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Lululemon"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br>The brand has made some efforts toward sustainability,<br>"
                                          +"such as using lower-impact materials, including recycled fabrics.<br>"
                                          +"However, these measures are insufficient. Lululemon's environmental<br>"
                                          +"impact has no evidence of meaningful action to reduce hazardous<br>"
                                          +"chemicals or protect biodiversity in its supply chain. Labor<br>"
                                          +"conditions data provide no evidence of ensuring payment of a living<br>"
                                          +"wage to workers and insufficient steps to address links to cotton<br>"
                                          +"sourced from regions at risk of forced labor. Additionally, Lululemon's<br>"
                                          +"animal welfare policies are lacking, with the use of materials like<br>"
                                          +"wool and exotic animal hair without clear policies to minimize<br>"
                                          +"animal suffering. Overall, Lululemon's efforts toward sustainability and<br>"
                                          +"ethical practices are inadequate and lack the necessary depth and commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 350);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/lululemon.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 305, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Motel Rocks"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"with limited use of eco-friendly materials and no substantial initiatives<br>"
                                          +"to reduce carbon emissions or waste. There is a lack of transparency<br>"
                                          +"regarding labor practices, with no evidence of ensuring fair wages or<br>"
                                          +"safe working conditions throughout its supply chain. Additionally,<br>"
                                          +"Motel Rocks lacks clear policies on animal welfare, using materials<br>"
                                          +"like leather and wool without information on sourcing or ethical<br>"
                                          +"standards. Overall, Motel Rocks' approach to sustainability and<br>"
                                          +"ethical practices is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/motelRock.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(200, 250, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 560, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 560, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Nasty Gal"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"using few lower-impact materials and following an unsustainable<br>"
                                          +"fast fashion model with quickly changing trends and regular new styles.<br>"
                                          +"There is no evidence of meaningful action to reduce or eliminate<br>"
                                          +"hazardous chemicals in manufacturing. Nasty Gal's labor <br>"
                                          +"practices provide no evidence of ensuring living wages for workers<br>"
                                          +"or supporting diversity and inclusion. The brand uses materials<br>"
                                          +"like leather and decorative feathers without clear policies to minimize<br>"
                                          +"animal suffering. Overall, Nasty Gal's approach to sustainability<br>"
                                          +"and ethical practices is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/nastyGal.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }

        if(brandName.equals("Nike"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made notable progress in sustainability,<br>"
                                          +"using some lower-impact materials, including recycled fabrics.<br>"
                                          +"It has set a target to eliminate most hazardous chemicals by 2025<br>"
                                          +"and is on track to meet this goal. However, Nike's fast fashion model<br>"
                                          +"contributes to overconsumption and waste. The brand's labor practices<br>"
                                          +"provide a social auditing program certified by the Fair Labor Association.<br>"
                                          +"It received a score of 51-60% in the 2022 Fashion Transparency Index.<br>"
                                          +"While Nike claims to have a program to improve wages, there's no<br>"
                                          +"evidence it ensures workers are paid living wages in most of its<br>"
                                          +"supply chain. Additionally, Nike uses materials like leather and wool<br>"
                                          +"without clear policies to minimize animal suffering.<br>"
                                          +"Overall, Nike's efforts toward sustainability and ethical practices<br>"
                                          +"show progress but still have room for improvement.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 330);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/nike3.jpeg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 310, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 560, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 560, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Nordstrom"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"using few eco-friendly materials and lacking initiatives to<br>"
                                          +"minimize textile waste in manufacturing. There is no evidence<br>"
                                          +"of meaningful action to reduce or eliminate hazardous chemicals or<br>"
                                          +"implement water reduction initiatives. Nordstrom received a low score<br>"
                                          +"of 21-30% in the 2021 Fashion Transparency Index, indicating limited<br>"
                                          +"disclosure of policies and practices. Additionally, the brand<br>"
                                          +"uses materials like leather, down, and exotic animal hair<br>"
                                          +"without clear policies to minimize animal suffering.<br>"
                                          +"Overall, Nordstrom's approach to sustainability and ethical practices<br>"
                                          +"is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/nordstrom.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Old Navy"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made some progress in sustainability,<br>"
                                          +"using lower-impact materials like recycled fabrics. It has set<br>"
                                          +"science-based targets to reduce greenhouse gas emissions and<br>"
                                          +"claims to be on track. However, Old Navy follows a fast fashion model,<br>"
                                          +"contributing to overconsumption and waste. Labor practices<br>"
                                          +"are rated 'It's a Start', with a score of 41-50% in the 2023 Fashion<br>"
                                          +" Transparency Index. There is no evidence of ensuring living wages for<br>"
                                          +"workers in most of its supply chain. Additionally, Old Navy<br>"
                                          +"uses materials like leather and wool without clear policies to minimize<br>"
                                          +"animal suffering. Overall, Old Navy demonstrates moderate progress<br>"
                                          +"but still has room for improvement in sustainability and ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/oldNavy.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);


            for (int i = 0; i < 3; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Organic Basics"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand demonstrates a strong commitment to sustainability,<br>"
                                          +"using a high proportion of lower-impact materials, including organic.<br>"
                                          +"cotton It employs low-impact materials that help limit chemicals,<br>"
                                          +" water, and wastewater in its supply chain. However, there is no evidence<br>"
                                          +"it has a policy to protect biodiversity or a science-based greenhouse gas<br>"
                                          +"emissions reduction target. Labor practices use sourcing from<br>"
                                          +"countries at extreme risk of labor abuse and no evidence of providing<br>"
                                          +"financial security to suppliers. On the positive side, Organic Basics<br>"
                                          +"uses recycled wool in all its wool products and avoids materials like<br>"
                                          +"leather, down, fur, angora, and exotic animal skin. Overall, Organic<br>"
                                          +" Basics shows commendable efforts toward sustainability and ethical practices<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/organic3.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 220, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 4; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }

        if(brandName.equals("PacSun"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"using few lower-impact materials and lacking initiatives to minimize<br>"
                                          +"textile waste in its supply chain. There is no evidence of meaningful<br>"
                                          +" action to reduce or eliminate hazardous chemicals in manufacturing.<br>"
                                          +"Labor practices provide no evidence of ensuring living wages for<br>"
                                          +"workers or supporting diversity and inclusion.<br>"
                                          +"Additionally, PacSun uses materials like leather and wool<br>"
                                          +"without clear policies to minimize animal suffering.<br>"
                                          +"Overall, PacSun's approach to sustainability and ethical practices<br>"
                                          +"is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/pacSun.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("PrettyLittleThing"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"using few eco-friendly materials and following an unsustainable<br>"
                                          +"fast fashion model with quickly changing trends and regular new styles.<br>"
                                          +"There is no evidence of meaningful action to reduce or eliminate<br>"
                                          +"hazardous chemicals in manufacturing. PrettyLittleThing's labor<br>"
                                          +"practices gives little of its supply chain certified by labor standards<br>"
                                          +"ensuring worker health, safety, or living wages. It received a score<br>"
                                          +"of 21-30% in the 2022 Fashion Transparency Index, indicating limited<br>"
                                          +"disclosure of policies and practices. The brand uses materials like<br>"
                                          +"leather, down, and wool without clear policies to minimize animal<br>"
                                          +"suffering. Overall, PrettyLittleThing's approach to sustainability and<br>"
                                          +"ethical practices is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 350);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/pretty.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 305, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 555, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 555, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Princess Polly"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made some efforts toward sustainability,<br>"
                                          +"such as using lower-impact materials, including recycled fabrics.<br>"
                                          +"However, these measures are insufficient. Princess Polly follows an<br>"
                                          +"unsustainable fast fashion model with quickly changing trends and<br>"
                                          +"regular new styles. There is no evidence it's taking meaningful action<br>"
                                          +"to reduce or eliminate hazardous chemicals in manufacturing. The<br>"
                                          +"brand's supply chain transparency is limited, with no evidence<br>"
                                          +"of ensuring payment of a living wage to workers. Additionally, Princess<br>"
                                          +"Polly uses materials like leather without clear policies to<br>"
                                          +"minimize animal suffering. Overall, Princess Polly's<br>"
                                          +"efforts toward sustainability and ethical practices<br>"
                                          +"are inadequate and lack the necessary depth and commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/princessPolly.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 250, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 310, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 570, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 570, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Ralph Lauren"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made significant progress in sustainability,<br>"
                                          +"using lower-impact materials and setting science-based targets to<br>"
                                          +"reduce greenhouse gas emissions. It offers some circular initiatives,<br>"
                                          +"such as clothing recycling, but does not report on the results.<br>"
                                          +"Ralph Lauren traces most of its supply chain and has a formal<br>"
                                          +"statement on workers' rights, though there is no evidence of ensuring<br>"
                                          +"living wages. The brand uses Responsible Wool Standard-certified wool<br>"
                                          +"and avoids fur and exotic animal skins. Overall, Ralph Lauren<br>"
                                          +"demonstrates strong commitment to sustainability and ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/ralphLauren.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(350, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 4; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 1; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }
        if(brandName.equals("Romwe"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand follows an unsustainable fast fashion model,<br>"
                                          +"with no significant efforts to reduce waste, carbon emissions,<br>"
                                          +"or protect biodiversity. Its labor practices lack transparency, fair<br>"
                                          +" wages, and safety standards, scoring poorly on the Fashion Transparency<br>"
                                          +"Index. Animal welfare policies are minimal, with no traceability<br>"
                                          +"of materials like leather or wool. Overall, Romwe demonstrates<br>"
                                          +"inadequate commitment to sustainability and ethical practices.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/romwe.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }

        if(brandName.equals("Shein"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"with limited use of eco-friendly materials and no substantial initiatives<br>"
                                          +"to reduce carbon emissions or waste. There is a lack of transparency<br>"
                                          +" regarding labor practices, with no evidence of ensuring fair wages or<br>"
                                          +"safe working conditions throughout its supply chain. Additionally,<br>"
                                          +"Shein lacks clear policies on animal welfare, using materials like<br>"
                                          +"leather and wool without information on sourcing or ethical standards.<br>"
                                          +"Overall, Shein's approach to sustainability and ethical practices<br>"
                                          +"is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/shein2.jpeg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Steve Madden"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"The brand uses some recycled materials but lacks meaningful action<br>"
                                          +"to reduce hazardous chemicals or water usage.<br>"
                                          +"While it has set emission reduction goals, progress is unclear.<br>"
                                          +"Labor practices are inadequate, with no evidence of living wages<br>"
                                          +"and limited supply chain transparency.<br>"
                                          +"It uses materials like leather and feathers without clear ethical policies.<br>"
                                          +"Overall, Steve Madden's sustainability efforts remain insufficient.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/steveMadden.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Tilly's"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has made minimal efforts toward sustainability,<br>"
                                          +"with limited use of eco-friendly materials and no substantial initiatives<br>"
                                          +"to reduce carbon emissions or waste. There is a lack of transparency<br>"
                                          +"regarding labor practices, with no evidence of ensuring fair wages or<br>"
                                          +"safe working conditions throughout its supply chain. Additionally,<br>"
                                          +"Tillys lacks clear policies on animal welfare, using materials<br>"
                                          +"like leather and wool without information on sourcing or ethical<br>"
                                          +"standards. Overall, Tillys' approach to sustainability and ethical<br>"
                                          +" practices is inadequate and lacks meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/tillys.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 1; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 4; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Tommy Hilfiger"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br>The brand has made notable progress in sustainability,<br>"
                                     +"such as sourcing 97% of its cotton from sustainable sources in 2022<br>"
                                     +"and achieving 78% renewable energy coverage across global retail<br>"
                                     +"operations. However, these measures are still insufficient. Tommy Hilfiger's<br>"
                                     +"fast fashion model contributes to overconsumption and waste. There<br>"
                                     +"is no substantial evidence of meaningful action to eliminate hazardous<br>"
                                     +"chemicals in its supply chain. The brand's supply chain transparency<br>"
                                     +"is limited, with no evidence of ensuring payment of a living wage to<br>"
                                     +"workers. Additionally, Tommy Hilfiger uses materials like leather<br>"
                                     +" and wool without clear policies to minimize animal suffering.<br>"
                                     +"Overall, Tommy Hilfiger's efforts toward sustainability and ethical<br>"
                                     +"practices are inadequate and lack the necessary depth and commitment.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 350);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/tommyHill.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 3; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Tripulse"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand demonstrates exceptional commitment to sustainability<br>"
                                          +"by utilizing eco-friendly materials like TENCEL™ Lyocell, which is<br>"
                                          +"derived from sustainable wood sources. Tripulse ensures<br>"
                                          +"its entire final production stage is certified by the<br>"
                                          +"Global Organic Textile Standard (GOTS), promoting fair labor practices.<br>"
                                          +"The brand avoids plastic packaging and manufactures products<br>"
                                          +"closer to home to reduce the climate impact of long-distance shipping.<br>"
                                          +"Additionally, Tripulse's products are generally free<br>"
                                          +"of animal-derived materials, minimizing animal suffering.<br>"
                                          +"Overall, Tripulse exemplifies outstanding dedication<br>"
                                          +"to environmental responsibility and ethical practices.<br>"
                    + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/tripulse.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(350, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 5; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
        }


        if(brandName.equals("Under Armour"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand has initiated some sustainability efforts,<br>"
                                          +"such as incorporating recycled materials into its products<br>"
                                          +"and setting goals to reduce greenhouse gas emissions. However, these<br>"
                                          +"measures are insufficient. Under Armour's fast fashion model<br>"
                                          +"contributes to overconsumption and waste. There is no substantial<br>"
                                          +"evidence of meaningful action to eliminate hazardous chemicals in<br>"
                                          +"its supply chain. The brand's supply chain transparency is limited,<br>"
                                          +"with no evidence of ensuring payment of a living wage to workers.<br>"
                                          +"Additionally, Under Armour uses materials like leather and wool<br>"
                                          +"without clear policies to minimize animal suffering.<br>"
                                          +"Overall, Under Armour's efforts toward sustainability and ethical<br>"
                                          +"practices are inadequate and lack the necessary depth and commitment.<br>"
                                           + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 320);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/under.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Uniqlo"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                     +"<br><br><br><br>"
                                     +"The brand has made some progress in sustainability, such as using<br>"
                                     +"lower-impact materials, including recycled fabrics, and offering<br>"
                                     +"clothing recycling programs to consumers. However, these measures are<br>"
                                     +"insufficient. Uniqlo's fast fashion model contributes to overconsumption<br>"
                                     +"and waste. There is no substantial evidence of meaningful action<br>"
                                     +"to reduce or eliminate hazardous chemicals in its supply chain. The<br>"
                                     +"brand's supply chain transparency is limited, with no evidence of ensuring<br>"
                                     +"payment of a living wage to workers. Additionally, Uniqlo uses materials<br>"
                                     +"like leather and wool without clear policies to minimize animal suffering.<br>"
                                     +"Overall, Uniqlo's efforts toward sustainability and ethical practices<br>"
                                     +"are inadequate and lack the necessary depth and commitment.<br>"
                                     + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/uniqlo2.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 3; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
        }

        if(brandName.equals("Urban Outfitters"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>Urban Outfitters is given a 2-star rating.<br>"
                                          +"The brand incorporates some recycled materials in its products<br>"
                                          +"but adheres to a fast fashion model, which promotes overconsumption<br>"
                                          +"and environmental waste.<br>"
                                          +"There is limited transparency in its labor practices,<br>"
                                          +"with no evidence of paying living wages or ensuring fair treatment<br>"
                                          +"throughout its supply chain.<br>"
                                          +"Animal welfare policies are minimal, with the use of materials<br>"
                                          +"like leather and wool lacking clear sourcing or ethical standards.<br>"
                                          +"While Urban Outfitters has taken small steps toward sustainability,<br>"
                                          +"its efforts are inadequate and lack meaningful commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/urban.jpg")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        if(brandName.equals("Zara"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br>The brand has made some efforts toward sustainability,<br>"
                                          +"such as setting a science-based target to reduce greenhouse gas emissions<br>"
                                          +"and using some eco-friendly materials. However, these measures are<br>"
                                          +"insufficient. Zara's fast fashion model, characterized by<br>"
                                          +"on-trend styles and regular new arrivals, contributes to overconsumption<br>"
                                          +"and waste. There is no substantial evidence of meaningful action<br>"
                                          +"to minimize textile waste during manufacturing. The brand's supply<br>"
                                          +"chain transparency is limited, with no evidence of ensuring payment<br>"
                                          +"of a living wage to workers. Additionally, Zara uses materials like<br>"
                                          +"leather and exotic animal hair without clear policies to minimize<br>"
                                          +" animal suffering. Overall, Zara's efforts toward sustainability and<br>"
                                          +"ethical practices are inadequate and lack the necessary depth and commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 40, 400, 320);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/zara3.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 320, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++) {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 570, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 570, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }

        if(brandName.equals("Zumiez"))
        {
            JLabel brand = new JLabel("<html><div style='text-align: center;'>"
                                          +"<br><br><br><br>The brand's environmental efforts are rated as 'very poor',<br>"
                                          +"with insufficient information about its environmental policies.<br>"
                                          +"In terms of labor practices, Zumiez is rated 'not good enough',<br>"
                                          +"lacking evidence of worker empowerment initiatives<br>"
                                          +"and failing to ensure payment of a living wage in its supply chain.<br>"
                                          +"Regarding animal welfare, the brand is rated 'it's a start',<br>"
                                          +"using materials like leather and wool without clear policies<br>"
                                          +"to minimize animal suffering.<br>"
                                          +"Overall, Zumiez's approach to sustainability and ethical practices<br>"
                                          +"is inadequate and lacks the necessary depth and commitment.<br>"
                                          + "</div></html>");
            brand.setFont(oswald.deriveFont(13f));
            brand.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            brand.setBounds(-5, 30, 400, 300);
            mainContentPanel.add(brand);

            ImageIcon brandImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/zumiez.png")));
            Image scaledFact2 = brandImg.getImage().getScaledInstance(300, 200, 4); // Resize image smoothly
            brandImg = new ImageIcon(scaledFact2); // Update fact with resized image
            brandDisplay = new JLabel(brandImg);
            brandDisplay.setBounds(25, 300, 350, 300);
            mainContentPanel.add(brandDisplay);

            for (int i = 0; i < 2; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/filledStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }
            for (int i = 0; i < 3; i++)
            {
                ImageIcon emptyStar = new ImageIcon(Objects.requireNonNull(getClass().getResource("pictures/EmptyStar.png")));
                Image scaledFact = emptyStar.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Resize image smoothly
                emptyStar = new ImageIcon(scaledFact); // Update fact with resized image
                JLabel empty = new JLabel(emptyStar);
                empty.setBounds(startX, 550, 100, 100);
                mainContentPanel.add(empty);

                startX += 80;
            }

        }


        JButton goBackButton = new JButton("Go Back");
        goBackButton.setFont(lato.deriveFont(14f));
        goBackButton.setBackground(new Color(255,178,194));
        goBackButton.setForeground(Color.BLACK);
        goBackButton.setOpaque(true);
        goBackButton.setBorderPainted(false);
        goBackButton.setContentAreaFilled(true);
        goBackButton.setBounds(80, 550, 200, 40);
        goBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        goBackButton.setBounds(250, 700, 100, 30);

         /*
        JButton goBackButton = new JButton("GO BACK");
        goBackButton.setFont(oswald.deriveFont(18f));
        goBackButton.setBackground(new Color(0, 99, 73)); //green button
        goBackButton.setForeground(new Color(247, 248, 247)); //white text
        goBackButton.setBounds(80, 550, 200, 40);
        goBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
       */
        goBackButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e)
            {
                showMainView(); // Go back to the main view
            }
        });

        mainContentPanel.add(goBackButton);
        // Refresh the panel
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // Function to show the main review page
    private void showMainView()
    {
        mainContentPanel.removeAll(); // Clear the main content panel
        headerPanel.setVisible(true); // Show header panel
        navigationBar.setVisible(true); // Show navigation bar

        // Add introduction and other elements back
        mainContentPanel.add(introduction);
        mainContentPanel.add(details);
        mainContentPanel.add(brandBox);
        mainContentPanel.add(imgDisplay);

        // Refresh the panel
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
}
