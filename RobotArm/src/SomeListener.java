import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 
// Decompiled by Procyon v0.5.36
// 

class SomeListener implements ActionListener
{
    private Robot robot;
    
    public SomeListener(final Robot r) {
        this.robot = r;
    }
    
    @Override
    public void actionPerformed(final ActionEvent paramActionEvent) {
        if (paramActionEvent.getActionCommand().equals("SpeedUp")) {
            this.robot.speedUp(2);
        }
        else {
            this.robot.slowDown(2);
        }
    }
}
