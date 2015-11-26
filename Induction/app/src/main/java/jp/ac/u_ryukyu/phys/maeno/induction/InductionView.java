package jp.ac.u_ryukyu.phys.maeno.induction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import jp.ac.u_ryukyu.phys.maeno.physlib.DraggManageView;
import jp.ac.u_ryukyu.phys.maeno.physlib.MovingObject;
import jp.ac.u_ryukyu.phys.maeno.physlib.Vec2;

/**
 * Created by maeno on 15/11/17.
 */
public class InductionView extends DraggManageView {
    private int wx;
    private int hy;
    private Induction3DView view3d;
    private float Mz=-1.3f; // 磁石のz座標
    private float Cz=1.5f; // コイルのz座標
    private float Mv=0f;
    private float Cv=0f;
    private final float  R=1f;
    private Paint p=new Paint();
    private Magnet magnet;
    private Coil coil;

    public InductionView(Context context,Induction3DView view3) {
        super(context);
        view3d=view3;
        view3d.setZ(Mz, Cz,Mv,Cv);
        magnet=new Magnet(1f,Vec2.zero,0.2f);
        coil=new Coil(1f,Vec2.zero,0.2f);
        addObj(magnet);
        addObj(coil);
        magnet.setDraggManager();
        coil.setDraggManager();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wx=w; hy=h;
        int ww=((View)getParent()).getWidth();
        int hh=((View)getParent()).getHeight();
        if( ww-hh != w ) {
            this.setLayoutParams(new LinearLayout.LayoutParams(ww-hh, hh));
            view3d.setLayoutParams(new LinearLayout.LayoutParams(hh, hh));
        }
        float unit=h/8;
        coil.setR(unit);
        magnet.setR(unit);
        magnet.setCenterX(w*0.5f);
        magnet.setX(w * 0.5f);
        magnet.setY(h * 0.5f - Mz * unit);
        coil.setCenterX(w * 0.5f);
        coil.setX(w * 0.5f);
        coil.setY(h * 0.5f - Cz * unit);
    }
    public void stop() { nowGo=false; }
    public void start() { nowGo=true; handler_start();}

    @Override
    public boolean ClickOthers(Vec2 m) {
        return false;
    }
    private double I;
    @Override
    protected void writePrevious(Canvas canvas) {

        p.setColor(Color.WHITE);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), p);
        p.setColor(Color.BLACK);
        canvas.drawLine(wx * 0.5f, 0, wx * 0.5f, hy, p);
        canvas.drawLine(0, hy * 0.5f, wx, hy * 0.5f, p);
        Mz=(hy*0.5f-magnet.Y())/(hy/8);
        Cz=(hy*0.5f-coil.Y())/(hy/8);
        Mv=-magnet.Vy()/(hy/8);
        Cv=-coil.Vy()/(hy/8);
        I=(Mv-Cv)*(
                Math.pow((Cz-Mz-R)*(Cz-Mz-R)+R*R,-1.5) -Math.pow((Cz-Mz+R)*(Cz-Mz+R)+R*R,-1.5)
        );
//        magnet.write(canvas);
        coil.writeI(canvas,I,coil.Px(),coil.Py());
        super.writePrevious(canvas);
        if( !magnet.isDragged() ) {
            magnet.setV(Vec2.zero);
        }
        if( !coil.isDragged() ) {
            coil.setV(Vec2.zero);
        }
        magnet.writeV(canvas);
        coil.writeV(canvas);

        view3d.setZ(Mz, Cz, -magnet.Vy() / (hy / 8), -coil.Vy() / (hy / 8));
        view3d.setI(I);
        view3d.calcB();
    }

    @Override
    protected void setSituation() {

    }

    @Override
    protected void writeSubView(Canvas canvas) {

    }

    @Override
    protected void calcNextEach(MovingObject movingObject) {

    }

}
