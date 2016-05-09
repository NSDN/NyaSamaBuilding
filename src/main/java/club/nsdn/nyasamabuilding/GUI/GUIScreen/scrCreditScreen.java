package club.nsdn.nyasamabuilding.GUI.GUIScreen;

import net.minecraft.client.gui.GuiScreen;

/**
 * Created by Administrator on 2016/5/9.
 */
public class scrCreditScreen extends GuiScreen {

    public scrCreditScreen(){

    }

    public void initGui()
    {
        //每当界面被打开时调用
        //这里部署控件
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        //在这里绘制文本或纹理等非控件内容,这里绘制的东西会被控件(即按键)盖住.
        super.drawScreen(par1,par2,par3);
        //在这里绘制文本或纹理等非控件内容,这里绘制的东西会盖在控件(即按键)之上.
    }
}
