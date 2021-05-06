package D18130610;

import processing.core.*;

public class WaveForms 
{
    ShrutisVisual sv;
    float cy = 0;


    public WaveForms(ShrutisVisual shrutisVisual) 
    {
        this.sv = shrutisVisual;
        cy = this.sv.height / 2;
    }

    public void render()
    {
        sv.colorMode(PApplet.HSB);
        for(int i = 0 ; i < sv.getAudioBuffer().size() ; i ++)
        {
            sv.stroke(
                PApplet.map(i, 0, sv.getAudioBuffer().size(), 0, 255)
                , 255
                , 255
            );

            sv.line(i, cy, i, cy + cy * sv.getAudioBuffer().get(i));
        }
    }
}
