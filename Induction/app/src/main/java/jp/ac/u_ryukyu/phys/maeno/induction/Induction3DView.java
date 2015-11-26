package jp.ac.u_ryukyu.phys.maeno.induction;

import android.content.Context;

import jp.ac.u_ryukyu.phys.maeno.physlib3d.DraggableRenderer;
import jp.ac.u_ryukyu.phys.maeno.physlib3d.DraggableSurfaceView;

/**
 * Created by maeno on 15/11/17.
 */
public class Induction3DView extends DraggableSurfaceView {
    private float Cz;
    private float Mz;
    private float Cv;
    private float Mv;
    public Induction3DView(Context context) {
        super(context);

    }
    @Override
    protected DraggableRenderer newRenderer() {
        return new Induction3DRenderer();
    }

    public void setZ(float mz, float cz,float mv,float cv) {
        Mz=mz;
        Cz=cz;
        Cv=cv;
        Mv=mv;
        ((Induction3DRenderer)renderer).setZ(mz,cz,mv,cv);
    }
    public void setI(double i){((Induction3DRenderer)renderer).setI(i);}

    public void calcB() {
        ((Induction3DRenderer)renderer).calcB();
    }
}
