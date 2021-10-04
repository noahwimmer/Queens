package core.main;

import core.utils.Constants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Space extends GameObject {

    public static LinkedList<Space> spaces = new LinkedList<>();

    /*
    Possible states:
        0 - free (blank)
        1 - queen (green)
        2 - invalid (red x)
     */
    int state = 0;

    int width = Constants.WIDTH/8;
    int height = Constants.HEIGHT/8;

    private Main main;

    public Space(float x, float y, int id, Handler handler, Main main) {
        super(x, y);
        this.id = id;
        this.handler = handler;
        this.main = main;
    }

    public void setInvalidHelper(LinkedList<Space> spaces) {
        for (Space s : spaces) {
            setInvalidSpaces(s.getID());
        }
    }

    // given the current id, set other necessary spaces as invalid
    public void setInvalidSpaces(int id) {
        int temp = id;
        // set all spaces above invalid
        while(temp >= 0) {
            temp-=10;
            try{
                main.getSpacebyId(temp).state = 2;
            } catch (NullPointerException e) {break;};
        }
        temp = id;
        //set all spaces below invalid
        while(temp < 80) {
            temp += 10;
            try{
                main.getSpacebyId(temp).state = 2;
            } catch (NullPointerException e) {break;};
        }

        temp = id % 10;
        int row = id - temp;
        //set all spaces left as invalid
        while(temp >= 0) {
            temp--;
            try{
                main.getSpacebyId(temp + row).state = 2;
            } catch (NullPointerException e) {break;};
        }

        //set all spaces right as invalid
        temp = id % 10;
        row = id - temp + 7;
        while(temp <= 7) {
            temp++;
            try{
                main.getSpacebyId(temp + row - 7).state = 2;
            } catch (NullPointerException e) {break;}
        }

        temp = id;
        //set all spaces up left as invalid
        while(temp > 0) {
            temp -= 11;
            try{
                main.getSpacebyId(temp).state = 2;
            } catch (NullPointerException e) {break;};
        }

        temp = id;
        // set all spaces down left as invalid
        while(temp <= 67) {
            temp += 9;
            try{
                main.getSpacebyId(temp).state = 2;
            } catch (NullPointerException e) {break;};
        }

        temp = id;
        //set all spaces up right as invalid
        while(temp >= 1) {
            temp -= 9;
            try{
                main.getSpacebyId(temp).state = 2;
            } catch (NullPointerException e) {break;};
        }

        temp = id;
        // set all spaces down right as invalid
        while(temp <= 66) {
            temp += 11;
            try{
                main.getSpacebyId(temp).state = 2;
            } catch (NullPointerException e) {break;};
        }
    }

    public void setValidSpaces(int id) {
        int temp = id;
        // set all spaces above invalid
        while(temp >= 0) {
            temp-=10;
            try{
                main.getSpacebyId(temp).state = 0;
            } catch (NullPointerException e) {break;};
        }
        temp = id;
        //set all spaces below invalid
        while(temp < 80) {
            temp += 10;
            try{
                main.getSpacebyId(temp).state = 0;
            } catch (NullPointerException e) {break;};
        }

        temp = id % 10;
        int row = id - temp;
        //set all spaces left as invalid
        while(temp >= 0) {
            temp--;
            try{
                main.getSpacebyId(temp + row).state = 0;
            } catch (NullPointerException e) {break;};
        }

        //set all spaces right as invalid
        temp = id % 10;
        row = id - temp + 7;
        while(temp <= 7) {
            temp++;
            try{
                main.getSpacebyId(temp + row - 7).state = 0;
            } catch (NullPointerException e) {break;}
        }

        temp = id;
        //set all spaces up left as invalid
        while(temp > 0) {
            temp -= 11;
            try{
                main.getSpacebyId(temp).state = 0;
            } catch (NullPointerException e) {break;};
        }

        temp = id;
        // set all spaces down left as invalid
        while(temp <= 67) {
            temp += 9;
            try{
                main.getSpacebyId(temp).state = 0;
            } catch (NullPointerException e) {break;};
        }

        temp = id;
        System.out.println("UR temp: " + temp);
        //set all spaces up right as invalid
        while(temp >= 1) {
            temp -= 9;
            try{
                main.getSpacebyId(temp).state = 0;
            } catch (NullPointerException e) {break;};
        }

        temp = id;
        // set all spaces down right as invalid
        while(temp <= 66) {
            temp += 11;
            try{
                main.getSpacebyId(temp).state = 0;
            } catch (NullPointerException e) {break;};
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else {
            return false;
        }
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();


        if(mouseOver(mx, my, (int) x, (int) y, width, height)) {
            //clicked on this space
            if(state == 2) {
                //state is invalid do nothing
            } else if(state == 1) {
                System.out.println("clicked again");
                //state is queen, set as free and return valid spaces
                state = 0;
                spaces.remove(this);
                setValidSpaces(id);
            } else if(state == 0) {
                System.out.println("clicked");
                // state is open, set as queen and set all other necessary invalid spaces
                state = 1;
                spaces.add(this);
            }
        }
    }

    int count = 0;
    @Override
    public void tick() {
        count++;
        if(count == 15) {
            setInvalidHelper(spaces);
            count = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        //draw border
        g.setColor(Color.WHITE);
        g.drawRect((int) x,(int) y, width, height);

        g.drawString(Integer.toString(id), (int) x, (int) y + 10);

        if(state == 1) {
            g.setColor(Color.GREEN);
            g.fillRect((int) x+10, (int) y+10, width-20 , height - 20);
        } else if(state == 2) {
            g.setColor(Color.RED);
            g.drawLine((int) x, (int) y, (int) x+width, (int) y+height);
            g.drawLine((int) x, (int) y+height, (int) x+width, (int) y);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
