package D18130610;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;


public class ShrutisVisual<cubes> extends Visual 
{
    float y = 200;
    float lerpedY = y;
    float[] lerpedBuffer;

    int which = 0;
    public void settings()
    {
        size(1024, 600);
        //fullScreen(P3D, SPAN);
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


    private float angle = 0;
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
        /*for (int i = 0; i < getAudioBuffer().size(); i++)
        {
            line(i, halfHeight, i , halfHeight + getAudioBuffer().get(i));
            //println(getAudioBuffer().get(i));
        }*/
        for (int i = 0; i < getAudioBuffer().size(); i ++)
        {
            sum += abs(getAudioBuffer().get(i));
        }
        average = sum / getAudioBuffer().size();


        switch (which)
        {
            case 0:
            {
                // Iterate over all the elements in the audio buffer
                for (int i = 0; i < getAudioBuffer().size(); i++) 
                {

                    float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    stroke(c, 255, 255);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);
        
                    line(i, halfHeight - lerpedBuffer[i] * halfHeight * 4, halfHeight + lerpedBuffer[i] * halfHeight * 4, i);
                }        
                break;
            }   
            case 1:
            {
                // Iterate over all the elements in the audio buffer
                for (int i = 0; i < getAudioBuffer().size(); i++) 
                {

                    float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    stroke(c, 255, 255);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);        
                    line(i, halfHeight - lerpedBuffer[i] * halfHeight * 4, i, halfHeight + lerpedBuffer[i] * halfHeight * 4);
                }        
                break;
            }

            case 2:
            {
                float r = 1f;
                int numPoints = 3;
                float thetaInc = TWO_PI / (float) numPoints;
                strokeWeight(2);                
                float lastX = width / 2, lastY = height / 2;
                for(int i = 0 ; i < 1000 ; i ++)
                {
                    float c = map(i, 0, 300, 0, 255) % 255.0f;
                    stroke(c, 255, 255, 100);
                    float theta = i * (thetaInc + lerpedAverage * 5);
                    float x = width / 2 + sin(theta) * r;
                    float y = height / 2 - cos(theta) * r;
                    r += 0.5f + lerpedAverage;
                    line(lastX, lastY, x, y);
                    lastX = x;
                    lastY = y;
                }
                break;
            }

            case 3:
            {
                float c = map(average, 0, 1, 0, 255);
                stroke(c, 255, 255);        
                strokeWeight(2);
                noFill();
                rectMode(CENTER);
                float size = 50 + (lerpedAverage * 500);

                for (int i = 0; i < getAudioBuffer().size(); i ++)
                {
                    rect(width / 2, height / 2, size + getAudioBuffer().get(i), size);
                }
                break;
            }

            case 4:
            {
                
                for (int i = 0; i < getAudioBuffer().size(); i++) {

                    float c1 = map(i, 0, getAudioBuffer().size(), 0, 255);

                    stroke(c1, 255, 255);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);        
                    line(0, i, lerpedBuffer[i] * halfHeight * 4, i);
                    line(width, i, width - (lerpedBuffer[i] * halfHeight * 4), i);
                    line(i, 0, i, lerpedBuffer[i] * halfHeight * 4);
                    line(i, height, i, height - (lerpedBuffer[i] * halfHeight * 4));
                }        
                float c2 = map(average, 0, 1, 0, 255);
                stroke(c2, 255, 255);        
                strokeWeight(2);
                noFill();
                rectMode(CENTER);
                float size = 50 + (lerpedAverage * 500);

                for (int j = 0; j < getAudioBuffer().size(); j ++)
                {
                    rect(width / 2, height / 2, size + getAudioBuffer().get(j), size);
                    rect(width / 2, height / 2, size + getAudioBuffer().get(j) + 50, size + 50);
                }

                stroke(150, c2, 255);
                size = 100 + (lerpedAverage * 1000);
                for (int i = 0; i < getAudioBuffer().size(); i ++)
                {
                    rect(width / 2, height / 2, size + getAudioBuffer().get(i)+100, size+100);
                    rect(width / 2, height / 2, size + getAudioBuffer().get(i) + 150, size + 150);
                }
                break;
            }

            case 5:
            {

                for (int i = 0; i < getAudioBuffer().size(); i++) 
                {
                    float c = map(average, 0, 1, 0, 255);
                    //float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    stroke(c, 255, 255);        
                    strokeWeight(2);
                    noFill();
                    rectMode(CENTER);
                    float size = 50 + (lerpedAverage * 500);
                    rect(width / 2, height / 2, size, size);

                   // float c = map(i, 0, getAudioBuffer().size(), 0, 255);
                    //stroke(c, 255, 255);
                    lerpedBuffer[i] = lerp(lerpedBuffer[i], getAudioBuffer().get(i), 0.1f);        
                    line(i, halfHeight - lerpedBuffer[i] * halfHeight * 4, i, halfHeight + lerpedBuffer[i] * halfHeight * 4);
                    
                } 
                break;
            }

            default:
            {
                // See the difference lerping makes? It smooths out the jitteryness of average, so the visual looks smoother
                //ellipse(width / 4, 100, 50 + average * 500, 50 + average * 500);
        
                float c = map(average, 0, getAudioBuffer().size(), 0, 255);
                stroke(c, 255, 255);        
                strokeWeight(2);
                noFill();
                ellipse(width / 2, halfHeight, 50 + (lerpedAverage * 500), 50 + (lerpedAverage * 500));
                
                break;
            }

        
        }
    }

}