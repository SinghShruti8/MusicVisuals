package D18130610;

import processing.core.*;

public class AudioBands {
    
    ShrutisVisual sv;

    public AudioBands(ShrutisVisual shrutisVisual)
    {
        this.sv = shrutisVisual; 
    }

    public void render()
    {
        float gap = sv.width / (float) sv.getBands().length;
        sv.noStroke();
        for(int i = 0 ; i < sv.getBands().length ; i ++)
        {
            sv.fill(PApplet.map(i, 0, sv.getBands().length, 255, 0), 255, 255);
            sv.rect(i * gap, sv.height, gap,-sv.getSmoothedBands()[i] * 0.2f); 
        }
    }
}
