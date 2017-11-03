package club.nsdn.nyasamabuilding.GUI.GUIScreen;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 */
public class scrCreditScreen extends GuiScreen {

    private ArrayList<String> CreditString = new ArrayList<String>();

    public scrCreditScreen(){

    }

    public void initGui()
    {
        //每当界面被打开时调用
        //这里部署控件
        String sCurrentLine = "";
        try {
            ResourceLocation rl = new ResourceLocation("nyabuilding:texts/credit.txt");
            IResourceManager irm = this.mc.getResourceManager();
            InputStream is = irm.getResource(rl).getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is,Charsets.UTF_8));
            while ((sCurrentLine = br.readLine()) != null) {
                CreditString.add(sCurrentLine);
            }
        }catch (IOException e){
            CreditString.add("Error: No such file or directory: 'credit.txt' ");
        }
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        //在这里绘制文本或纹理等非控件内容,这里绘制的东西会被控件(即按键)盖住.
        super.drawScreen(par1,par2,par3);
        //在这里绘制文本或纹理等非控件内容,这里绘制的东西会盖在控件(即按键)之上.
        drawCenteredString(fontRendererObj, "Credit", width/2, (int)(height*0.05), 0xFFFF00);
        /*drawString(fontRendererObj, String.format("You are pointing to: (§o%d§r,§o%d§r)", par1, par2),
                (int)(width*0.05), (int)(height*0.9), 0xFFFFFF);*/
        int i = (int)(height*0.2);
        for(String si:CreditString){
            drawString(fontRendererObj,si,(int)(width*0.05),i,0xFFFFFF);
            i += 10;
        }
    }
}
