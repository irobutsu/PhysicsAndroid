 package jp.ac.u_ryukyu.phys.maeno.physlib3d;

 import java.nio.ByteBuffer;
 import java.nio.ByteOrder;

 import javax.microedition.khronos.opengles.GL10;

 import jp.ac.u_ryukyu.phys.maeno.physlib.Vec3;

 public class Cylinder extends MovingObject3D{
     Vec3 tenjo[];
     Vec3 soko[];
     Vec3 sokoC;
     Vec3 org_tenjo[];
     Vec3 org_soko[];
     Vec3 org_sokoC;

     float R;
     float h;
     int Rnum;


     public Cylinder(float RR,float hh,int RRnum,float r,float g,float b,float a) {
         super(r,g,b,a);
         R=RR; h=hh;
         Rnum=RRnum;

         makePts();
         makevertices();
     }
     protected void makePts() {
         tenjo=new Vec3[Rnum];
         soko=new Vec3[Rnum];
         sokoC=new Vec3(0f,0f,0f);
         org_tenjo=new Vec3[Rnum];
         org_soko=new Vec3[Rnum];
         org_sokoC=new Vec3(0f,0f,0f);
         int i;
         double omega=2.0*Math.PI/Rnum;
         for(i=0; i<Rnum; i++) {
             float x=(float)(R*Math.cos(i*omega));
             float y=(float)(R*Math.sin(i*omega));
             org_tenjo[i]=new Vec3(x,y,h);
             org_soko[i]=new Vec3(x,y,0f);
         }
         copyFromOrg();
     }
     @Override
     public void copyFromOrg() {
         int i;
         for(i=0; i<Rnum ;i++) {
             tenjo[i]=new Vec3(org_tenjo[i]);
             soko[i]=new Vec3(org_soko[i]);
         }
         sokoC=new Vec3(org_sokoC);
     }
     @Override
     public void setThetaPts(float theta) {
         copyFromOrg();
         int i;
         for(i=0; i<Rnum ;i++) {
             tenjo[i].roty(theta);
             soko[i].roty(theta);
         }
         sokoC.roty(theta);
         makevertices();
     }
     @Override
     public void setPhiPts(float phi) {
         copyFromOrg();
         int i;
         for(i=0; i<Rnum ;i++) {
             tenjo[i].rotz(phi);
             soko[i].rotz(phi);
         }
         sokoC.rotz(phi);
         makevertices();
     }
     @Override
     public void setThetaPhiPts(float theta,float phi) {
         copyFromOrg();
         int i;
         for(i=0; i<Rnum ;i++) {
             tenjo[i].roty(theta);
             tenjo[i].rotz(phi);
             soko[i].roty(theta);
             soko[i].rotz(phi);
         }
         sokoC.roty(theta);
         sokoC.rotz(phi);
         makevertices();
     }

     protected void makevertices() {
         // Rnum*2個の点ができたので、面を作る。
         vertices=new float[12*Rnum];
         int i;

         for(i=0; i<Rnum-1; i++) {
             vertices[12*(i)]=tenjo[i].X();
             vertices[12*(i)+1]=tenjo[i].Y();
             vertices[12*(i)+2]=tenjo[i].Z();
             vertices[12*(i)+3]=tenjo[i+1].X();
             vertices[12*(i)+4]=tenjo[i+1].Y();
             vertices[12*(i)+5]=tenjo[i+1].Z();
             vertices[12*(i)+6]=soko[i].X();
             vertices[12*(i)+7]=soko[i].Y();
             vertices[12*(i)+8]=soko[i].Z();
             vertices[12*(i)+9]=soko[i+1].X();
             vertices[12*(i)+10]=soko[i+1].Y();
             vertices[12*(i)+11]=soko[i+1].Z();
         }
         vertices[12*(Rnum-1)]=tenjo[Rnum-1].X();
         vertices[12*(Rnum-1)+1]=tenjo[Rnum-1].Y();
         vertices[12*(Rnum-1)+2]=tenjo[Rnum-1].Z();
         vertices[12*(Rnum-1)+3]=tenjo[0].X();
         vertices[12*(Rnum-1)+4]=tenjo[0].Y();
         vertices[12*(Rnum-1)+5]=tenjo[0].Z();
         vertices[12*(Rnum-1)+6]=soko[Rnum-1].X();
         vertices[12*(Rnum-1)+7]=soko[Rnum-1].Y();
         vertices[12*(Rnum-1)+8]=soko[Rnum-1].Z();
         vertices[12*(Rnum-1)+9]=soko[0].X();
         vertices[12*(Rnum-1)+10]=soko[0].Y();
         vertices[12*(Rnum-1)+11]=soko[0].Z();

         ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
         vbb.order(ByteOrder.nativeOrder());
         mVertexBuffer = vbb.asFloatBuffer();
         mVertexBuffer.put(vertices);
         mVertexBuffer.position(0);
     }
     @Override
     public void translatePts(Vec3 p) {
         int i;
         for(i=0; i<Rnum ; i++) {
             tenjo[i].add(p);
             soko[i].add(p);
         }
         makevertices();
     }
     @Override
     public void drawHontai(GL10 gl){
         gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
         gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
         gl.glColor4f(rr,gg,bb,aa);
         int i;
         for(i=0; i<Rnum ; i++ ) {
             int ii=i+1;
             if( ii==Rnum ) {
                 ii=0;
             }

             Vec3 n=Vec3.NormalizedNormal(tenjo[i],soko[ii],tenjo[ii]);
             gl.glNormal3f(n.X(), n.Y(),n.Z());
             gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*4, 4);
         }
         // 底と天井の描写を忘れている。
     }
 }