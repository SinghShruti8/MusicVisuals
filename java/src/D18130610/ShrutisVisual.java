package D18130610;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;


public class ShrutisVisual extends Visual 
{
    float y = 200;
    float lerpedY = y;
    float[] lerpedBuffer;

    int which = 0;

    public void settings()
    {
        size(1024, 600);
        
    }

    public void setup()
    {
        startMinim();
        loadAudio("mixkit-deep-urban-623.mp3");
        lerpedBuffer = new float[width];
    }

    public void keyPressed()
    {
        if (key >= '0' && key <= '6')
        {
            which = key - '0';
        }

        if (key == ' ') 
        {
            if (getAudioPlayer().isPlaying())
            {
                getAudioPlayer().pause();
            }
            else
            {
                getAudioPlayer().play();
            }
        }
    }

    
    float lerpedAverage = 0;

    public void draw()
    {
        float average = 0;
        float sum = 0;

        try 
        {
            calculateFFT();
        } 
        catch (VisualException e) 
        {
            e.printStackTrace();
        }


        background(0);
        stroke(255);
        float halfHeight = height / 2;

        for (int i = 0; i < getAudioBuffer().size(); i ++)
        {
            sum += abs(getAudioBuffer().get(i));
        }
        average = sum / getAudioBuffer().size();


        switch (which)
        {
            case 0:
            {
                //Waves through the rectangle
                for (int i = 0; i < getAudioBuffer().size(); i++) 
                {
                    float c1 = map(average, 0, 1, 0, 255);
                    stroke(c1, 255, 255);        
                    strokeWeight(2);
                    noFill();
                    rectMode(CENTER);
                    float size = 50 + (lerpedAverage * 500);
                    rect(width / 2, height / 2, size, size);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);        
                    line(i, halfHeight - lerpedBuffer[i] * halfHeight * 4, i, halfHeight + lerpedBuffer[i] * halfHeight * 4);
                    
                }     
                break;
            }   
            case 1:
            {
                //waves from the bottom
                for (int i = 0; i < getAudioBuffer().size(); i++) 
                {

                    float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    stroke(c, 255, 255);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);        
                    line(i, halfHeight - lerpedBuffer[i] * halfHeight * 2, i, halfHeight + lerpedBuffer[i] * halfHeight * 2);
                    line(i, halfHeight - lerpedBuffer[i] * 2, height*1.8f - i, height *2.0f + lerpedBuffer[i] * halfHeight * 2);
                }        
                break;
            }

            case 2:
            {
                //3D looking waves
                //side view
                strokeWeight(2);

                for (int i = 0; i < getAudioBuffer().size(); i++) 
                {

                    float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    stroke(c, 255, 255,100);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f); 
                    triangle(i, halfHeight - lerpedBuffer[i] * halfHeight * 2, i+halfHeight, halfHeight + lerpedAverage * 5, i, halfHeight - lerpedBuffer[i] * halfHeight * 2);

                }
                break;
            }

            case 3:
            {
                //moving rectangles
                float c = map(average, 0, 1, 0, 255);
                stroke(c, 255, 255);        
                strokeWeight(2);
                noFill();
                rectMode(CENTER);
                float size = 50 + (lerpedAverage * 500);

                for (int i = 0; i < getAudioBuffer().size(); i ++)
                {
                    rect(width / 2, height / 2, size + getAudioBuffer().get(i), size);
                    rect(i, size + getAudioBuffer().get(i) * 100, width / 12, height / 4);
                    rect(height - i, size + getAudioBuffer().get(i) * 100, width / 12, height / 4);
                }
                break;
            }

            case 4:
            {
                //Rectangles and colorful waves.
                for (int i = 0; i < getAudioBuffer().size(); i++) {

                    float c1 = map(i, 0, getAudioBuffer().size(), 0, 255);

                    stroke(c1, 255, 255);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);        
                    line(0, i, lerpedBuffer[i] * halfHeight * 4, i);
                    line(width, i, width - (lerpedBuffer[i] * halfHeight * 4), i);
                    line(i, 0, i, lerpedBuffer[i] * halfHeight * 4);
                    line(i, height, i, height - (lerpedBuffer[i] * halfHeight * 4));
                }
                
                //Adding the rectangles at the center
                float c2 = map(average, 0, 1, 0, 255);
                stroke(c2, 255, 255);        
                strokeWeight(2);
                noFill();
                rectMode(CENTER);
                float size = 50 + (lerpedAverage * 500);
                
                //inner rectangles 
                for (int j = 0; j < getAudioBuffer().size(); j ++)
                {
                    rect(width / 2, height / 2, size + getAudioBuffer().get(j), size);
                    rect(width / 2, height / 2, size + getAudioBuffer().get(j) + 50, size + 50);
                }

                stroke(150, c2, 255);
                size = 100 + (lerpedAverage * 1000);

                //outer rectangles
                for (int i = 0; i < getAudioBuffer().size(); i ++)
                {
                    rect(width / 2, height / 2, size + getAudioBuffer().get(i)+100, size+100);
                    rect(width / 2, height / 2, size + getAudioBuffer().get(i) + 150, size + 150);
                }
                break;
            }

            case 5:
            {
                //Vertical waves with horizontal motion
                for (int i = 0; i < getAudioBuffer().size(); i++) 
                {

                    float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    stroke(c, 150, 255);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);
        
                    line(halfHeight - lerpedBuffer[i] * halfHeight * 4, i, halfHeight * lerpedBuffer[i] * halfHeight * 4, i);

                }     
                break;
            }

            default:
            {
                //moving Volume speaker
                for (int i = 0; i < getAudioBuffer().size(); i++)
                {
                    float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    stroke(c, 150, 255);        
                    strokeWeight(2);
                    noFill();
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);
                    ellipse(width / 2, halfHeight, 50 + (lerpedBuffer[i] * 500), 50 + (lerpedBuffer[i] * 500));
                }                
                
                break;
            }
        } //end switch

    }//end draw

}//end class