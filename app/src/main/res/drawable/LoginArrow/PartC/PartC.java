
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JSlider;
import javax.swing.border.*;
import java.lang.*;
public class PartC extends JFrame implements ActionListener
{
 //variables
 JLabel people;
 JLabel suicide;
 JLabel result;
 JLabel image;
 JLabel firstINline;
 
 JSlider No_people;
 JSlider No_suicide;
 
 JButton procced;
 
 JPanel mainPanel;
 JPanel setUpPanel;
 JPanel displayPanel;
 
 ImageIcon foreground = new ImageIcon("figure.png");
 ImageIcon arrow = new ImageIcon("BlueArrow.png");
 
 CardLayout layout = new CardLayout();// a cart layout for the Content Pane
 
 int PeopleNum;
 int suicideNum;
 int KitsosSpot;

 names Names = new names();
 
 TitledBorder titled;
 Font fontForDisplay = new Font("Serif",Font.PLAIN, 25);
 
 //the maximum of labels allowed by the program is 20 labels hence an array with 20 JLabels is created
 JLabel[] labels = {
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER),
          new JLabel(Names.generateName(),JLabel.CENTER)
        };   
 
 //constructor
 public PartC()
 {
    setTitle("Kitsos a smart greek");
    
    //varialbles  
    people = new JLabel("Number of People");
    people.setForeground(Color.yellow);  //changing the text color of the label to yellow
    suicide = new JLabel("Suicide sequence");
    suicide.setForeground(Color.yellow);  //changing the text color of the label to yellow
    firstINline = new JLabel(arrow);   
    image = new JLabel(foreground);
    
    No_people = new JSlider(0,20,0);  //creates a JSlider with values from 0 to 20 and initial value 0
    No_people.setMajorTickSpacing(2);  //sets a big tick spacing every two units
    No_people.setMinorTickSpacing(1);  // sets a small tick spacing every 1 unit
    No_people.setPaintLabels(true); //makes unit numbers to appear every big tick spacing
    No_people.setPaintTicks(true);  //makes the big and small ticks appear
    No_suicide = new JSlider(0,20,0);  //creates a JSlider with values from 0 to 20 and initial value 0
    No_suicide.setMajorTickSpacing(2);//sets a big tick spacing every two units
    No_suicide.setMinorTickSpacing(1);// sets a small tick spacing every 1 unit
    No_suicide.setPaintLabels(true);//makes unit numbers to appear every big tick spacing
    No_suicide.setPaintTicks(true);//makes the big and small ticks appear
    
    mainPanel = new JPanel();
    setUpPanel = new JPanel();
    displayPanel = new JPanel();
    
    procced = new JButton("Procced");
    procced.setBounds(120, 150, 80, 30); //determines the location of the top left corner of the button in the frame as well as its dimensions
    
    //creating and modifying various borders
    Border orangeline = BorderFactory.createLineBorder(Color.orange);// creating an orange line border
    TitledBorder titled = BorderFactory.createTitledBorder(orangeline,  "Alive");// creating an orange titled border
    titled.setTitleColor(Color.orange); //changing the color of the title to orange    
    Border matte1 = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.yellow); // ayellow border with a 5 pixel thickness on each side
    
    
    
    //makinkg the JLabels invicibe and adding a titled border to each of them
    for(int i = 0; i < 20; ++i)
    {
     labels[i].setVisible(false);
     labels[i].setBorder(titled);     
    }
    
    //modifying and adding components to the setUp panel
    setUpPanel.setBackground(new Color(9,1,1));
    setUpPanel.add(people);    
    setUpPanel.add(No_people);
    setUpPanel.add(suicide);
    setUpPanel.add(No_suicide);
    setUpPanel.add(image);
    setUpPanel.add(procced);    
    setUpPanel.setBorder(matte1);
    
    // modifying and adding components to displayPanel
    displayPanel.setLayout(null);// setting the layouot of display Panl as null in order to use Absolute Positioning later
    for (int i = 0; i < 20; ++i)
    {
     displayPanel.add(labels[i]);
    } 
    displayPanel.setBorder(matte1);
    displayPanel.setBackground(new Color (9,0,0));
    
    //ContentPane
    getContentPane().setLayout(layout); 
    getContentPane().add(setUpPanel, "setup");//the first card of the cart layout 
    getContentPane().add(displayPanel, "Graphical Display");//the second card of the card layout
    layout.show(getContentPane(), "setup"); //seting the setup card from the card layout to be displayed first
    
    //Action Listener
    procced.addActionListener(this);//there is only one button so there is no need to add a distinguishable action command
    
  
    }
    
 public void actionPerformed(ActionEvent e)
 {
   
    
    layout.show(getContentPane(), "Graphical Display");//when the butto is clicked the second card of the card layout is displayed
    PeopleNum = No_people.getValue(); //gets the value from the JSlider responsible for the number of people in the circle
    int number = PeopleNum; // a variable that will store the number of people in before the suicides begin
    

    suicideNum = No_suicide.getValue(); //gets the value from the JSlider responsible for the suicide sequence
    displayPanel.removeAll(); //removes everything that was previously in the display panel so that we can ad only those we need later
    
    //  creating and modyifing a label that will give the place where kitsos should stay
    result = new JLabel("Kitsos should sit at position: ");
    result.setBounds(43,5,395,40);
    result.setFont(fontForDisplay);
    result.setForeground(Color.yellow);
    result.setBackground(Color.black);
    displayPanel.add(result); //adding the newly created label to displayPanel
    
    //this part of the code is responsible for putting JLabels in a circle
    double SingleAngle = 360/PeopleNum; //the angle between two JLabels
    double ActualAngle; //the ang,e between the first JLabel (from which the counting starts) and the current JLabel to be displayed
    //creating an imaginary point in the JFrame with coordinates X and Y that will serve as the center of the circle for the displayed labels
    int CircleCenterX = 165; 
    int CircleCenterY = 295;
    int Radius = 160; //radius of the imaginary circle alongside which the labels will be displayed
    int X; //the X coordinate of the top left corner of the JLabel
    int Y; //the Y coordinate of the top left corner of the JLabel 
    
    for(int i = 0; i < PeopleNum; ++i)
    {
      ActualAngle  = SingleAngle * i;//the angle between the first label and the current label
      ActualAngle = Math.toRadians(ActualAngle); //converting that angle to rad
      X = (int) (Radius * Math.cos(ActualAngle)+ CircleCenterX); //using a mathematical formula to determain the X coordinate where the Actual angle intercepts the circle
      Y = (int) (Radius * Math.sin(ActualAngle) + CircleCenterY);//using a mathematical formula to determain the Y coordinate where the Actual angle intercepts the circle
      labels[i].setBounds(X,Y,60,40); //setting the coordinates and dimensons of the label
      labels[i].setForeground(Color.yellow); // modyifing the label
      displayPanel.add(labels[i]);// adding the label back to the display panel
      labels[i].setVisible(true);//making it visible
    }
    

    Border Redline = BorderFactory.createLineBorder(Color.red);// creating a red line border
    TitledBorder Titled1 = BorderFactory.createTitledBorder(Redline,  "Dead"); //creating a red titled border that will be used to distinguish those who commit suicide n the ircle
    Titled1.setTitleColor(Color.red);
    
    int i = 0;//i will keep track of the people's position in the circle during counting
    int counter = 1;  // counter will count up to the suicide sequence 
    //this section of the code is responsible for going through the suicide procces according to a given sequence
    while(PeopleNum > 1)//runs until there is only one person in the circle alive
    {
     if(i >= number) //if i reaches the lst person of the circle it goes to 0 so the counting can procced with the next person on the circle which is the first
      {
        i = 0;
        }
      if(labels[i].getBorder() != Titled1)//checks if the person is already deady or alive (if a label has Titled1 as a border the person is already dead)
      {
      if (counter % suicideNum == 0 ) //when the counter reaches the suicide sequence the person in the current position commits suicide 
      {
        labels[i].setBorder(Titled1);
        PeopleNum--; //since one person commited suicide there is one person less alive so number of people is decremented by 1
        }
        counter++;
     }
     i++;
    }
    
    //now that we know which position is going to survive we put Kitsos in that spot
    int a  = 0;
    while(true)
    {
     if(labels[a].getBorder()!=Titled1)
     {
        labels[a].setText("Kitsos");
        KitsosSpot  = a+1;
        break;
        }
     
     ++a;
    }
    result.setText("Kitsos should sit at position: " + KitsosSpot);
   
    
    firstINline.setBounds(180,250,140,122);//setting the position for the arrow to point at the first person from whom the counting starts
    displayPanel.add(firstINline);
    displayPanel.revalidate();//updating the panel
    displayPanel.repaint();//displaying the updated panel
   
    } 
    

    
 public static void main(String args[])
 {
    PartC myframe = new PartC();
    
    myframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
    myframe.setSize(405, 600);
   
    myframe.setResizable(false);
    //myframe.setLocation(150, 250);
    myframe.setVisible(true);
    }
}
