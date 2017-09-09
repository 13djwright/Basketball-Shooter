package cs335final;

import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
    private GLU glu = new GLU();
    private GLUT glut = new GLUT();
    
    float orthoX=40;
    int windowWidth, windowHeight;

	private Texture front;
	private Texture back;
	private Texture left;
	private Texture right;
	private Texture top;
	private Texture bottom;
	private Texture metal;
	private Texture ball;
	private Texture backboard;
	private Texture halfcourt;
	private Texture fence;
	private Texture arrow;
	private Texture water;
	private Texture lr_arrow;
	private Texture r_hud;
    private Texture title;
    private Texture gauge;
    private Texture hud_top;
    private Texture command;
    private Texture face_texture;
    private Texture torso;
	private Texture pants;
	private Texture skin;
	
	int mouseDragButton=0;
	float mouseX0 = 0.0f, mouseY0 = 0.0f;
	float mouseX1 = 0.0f , mouseY1 = 0.0f;
	
	boolean event = false;
	boolean r_click = false;
	boolean l_click = false;

	char key = 't';

	float x = 0;
 	float y = 0;
 	float z = 0;
 	
 	float mv_forward = 0; 
	float mv_right = 0;
	
	float factor = 50f;
	float c_factor = 1.0f;
	float m_factor = 10f;
	float h_factor = 2f;
	
	int chance = 0;

	Clip bg;
	Clip bg2;
	
	boolean start_game = false;
	
	public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
	}

    public void init(GLAutoDrawable gLDrawable) {
    	GL2 gl = gLDrawable.getGL().getGL2();
    	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
    	gl.glClearDepth(1.0f);                      // Depth Buffer Setup
    	gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
    	gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do   
        try {
        	front = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/city_bk.png"), false, "png");
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
        	back = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/city_ft.png"), false, "png");
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
        	left = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/city_rt.png"), false, "png");
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
        	right = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/city_lf.png"), false, "png");
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
        	top = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/city_up.png"), false, "png");
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
        	bottom = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/city_dn.png"), false, "png");
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
        	
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        //load box texture
        try {
        	backboard = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/backboard.png"), false, "png");
        	ball = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/ballcolor.png"), false, "png");
        	metal = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/metal.png"), false, "png");
        	halfcourt = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/halfcourt.png"), false, "png");
        	fence = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/fence.png"), false, "png");
        	arrow = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/arrow.png"), false, "png");
        	lr_arrow = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/left-right.png"), false, "png");
        	water = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/blue-water.png"), false, "png");
        	r_hud = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/replay_hud.png"), false, "png");
        	title = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/title_screen.png"), false, "png");
        	gauge = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/gauge.png"), false, "png");
        	hud_top = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/hud_top.png"), false, "png");
        	command = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/c_menu.png"), false, "png");
        	face_texture = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/man.png"), false, "png");
        	torso = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/torso.png"), false, "png");
        	pants = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/pants.png"), false, "png");
        	skin = TextureIO.newTexture(this.getClass().getResourceAsStream("textures/skin.png"), false, "png");

        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
        
       	 try {
     		 	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("bin/cs335final/sounds/title.wav").getAbsoluteFile());
                 bg = AudioSystem.getClip();
                 bg.open(audioInputStream);
                 bg.loop(Clip.LOOP_CONTINUOUSLY);
     		 }
     		 catch(Exception ex) {
     	        System.out.println("Error with playing sound.");
     	        ex.printStackTrace();
     	     }
        
       	 try {
 		 	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("bin/cs335final/sounds/background.wav").getAbsoluteFile());
             bg2 = AudioSystem.getClip();
             bg2.open(audioInputStream);
             
 		 }
 		 catch(Exception ex) {
 	        System.out.println("Error with playing sound.");
 	        ex.printStackTrace();
 	     }

        glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 1, 0);
	    final float h = (float) windowWidth / (float) windowHeight;
	    glu.gluPerspective(45.0f, h, 1.0, 1000000.0);
    }

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		windowWidth = width;
		windowHeight = height;
	    final GL2 gl = gLDrawable.getGL().getGL2();
	    if (height <= 0) // avoid a divide by zero error!
	        height = 1;
	    final float h = (float) width / (float) height;
	    gl.glViewport(0, 0, width, height);
	    gl.glMatrixMode(GL2.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluPerspective(45.0f, h, 1.0, 1000000.0);
	    glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
	    gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
	}
	
    public void drawSkybox(final GL2 gl) {
	    front.enable(gl);
		front.bind(gl);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(  factor, -factor, -factor );
	    gl.glTexCoord2f(1, 0); gl.glVertex3f( -factor, -factor, -factor );
	    gl.glTexCoord2f(1, 1); gl.glVertex3f( -factor,  factor, -factor );
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(  factor,  factor, -factor );
	    gl.glEnd();
	    front.disable(gl);
	    right.enable(gl);
		right.bind(gl);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(  factor, -factor, factor );
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(  factor, -factor, -factor);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f( factor,  factor, -factor);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(  factor,  factor,  factor);
	    gl.glEnd();
	    right.disable(gl);
	    back.enable(gl);
		back.bind(gl);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f( -factor, -factor,  factor );
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(  factor, -factor, factor );
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(  factor,  factor,  factor );
	    gl.glTexCoord2f(0, 1); gl.glVertex3f( -factor,  factor,  factor );
	    gl.glEnd();
	    back.disable(gl);
	    left.enable(gl);
		left.bind(gl);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f( -factor, -factor, -factor );
	    gl.glTexCoord2f(1, 0); gl.glVertex3f( -factor, -factor,  factor );
	    gl.glTexCoord2f(1, 1); gl.glVertex3f( -factor,  factor,  factor );
	    gl.glTexCoord2f(0, 1); gl.glVertex3f( -factor,  factor, -factor);
	    gl.glEnd();
	    left.disable(gl);
	    top.enable(gl);
		top.bind(gl);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f( -factor,  factor, -factor );
	    gl.glTexCoord2f(0, 0); gl.glVertex3f( -factor,  factor,  factor );
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(  factor,  factor,  factor);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(  factor,  factor, -factor );
	    gl.glEnd();
	    top.disable(gl);
	    bottom.enable(gl);
		bottom.bind(gl);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f( -factor, -factor, -factor);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f( -factor, -factor, factor);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(  factor, -factor, factor );
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(  factor, -factor, -factor);
	    gl.glEnd();
	    bottom.disable(gl);
    }

    double ball_x = 0;
    double ball_y = 0;
    double ball_z = 0;
    double ball_x_angle = 0;
    double ball_y_angle = 0;
    double ball_speed = 1300;
    double ball_speed_z = -ball_speed;
    double ball_speed_x = ball_speed;
    double ball_speed_y = ball_speed;
    double initial_ball_speed_z = -ball_speed;
    double initial_ball_speed_x = ball_speed;
    double initial_ball_speed_y = ball_speed;
    double initial_ball_x = 0;
    double initial_ball_y = 0;
    double initial_ball_z = 0;
    double time = 0;
    double time_speed = 0.0001;
    double time_speed_const = time_speed;
    boolean start_animation = false;
    double acceleration = -9;
    double post_radius = .4;
    double post_height = 12;
	float ball_size = 0.6f;
    double rim_size = ball_size*2;
    double rim_inner_radius = 0.1;
    float floor_height = -29f;
    float f_factor = 35f;
    float center_x = 0;
    float center_y = 0;
    float center_z = 0;
    float court_width = 42f/2;
    float court_length = 50f/2;
    float backboard_width = 4.0f;
    float backboard_height = 3.0f;
    float fence_height = 10.0f;
    double gravity = 9.8*10000;
    double bounce_height_percentage = 0.8;
    double bounce_length_percentage = 0.8;
    double min_bounce_height = -f_factor-floor_height;
    float rim_y = (float) (post_height-f_factor-floor_height-.80*backboard_height);
    double ball_speed_mult = 500;
    double ball_speed_add = 500;
    double max_left_right = 5.0;
    
    float post_z = (float) (2*post_radius+1.5f);
    float backboard_z = post_z + 0.5f;
    float rim_z = backboard_z + 0.5f;
    double friction = .9;
    boolean shot_hit = false;
    boolean hit_ground = false;
    
    float gauge_width = 0f;
	float max_gauge_w = 2.7f;
    float gauge_h = .8f;
    
    float arrow_dim = 1.2f;
    float arrow_pos_x = 3f;
    float arrow_pos_y = 1f;
    
    float lr_arrow_dim = 1.5f;
    float lr_arrow_pos_x = 10.5f;
    float lr_arrow_pos_y = 2f;
    
    float gauge_pos_x = 5f;
    float gauge_pos_y = 1f;
  //what makes the hud above all of the other elements
    float hud_factor = -court_width + 20;
    double last_z_collision = 0;
    boolean replay = false;
    boolean played_sound = false;
    
    String victory_loc = "bin/cs335final/sounds/victory.wav";
    String lose_loc = "bin/cs335final/sounds/lose.wav";
    boolean game_over = false;

    boolean maxed_out = false;
    boolean very_maxed_out = false;
    
    public void play_sound() {
    	//load sounds
    	AudioInputStream audioInputStream;
    	Clip victory;
    	Clip lose;
    	
    	if(shot_hit && !played_sound) {
    		try {
    			played_sound = true;
	            audioInputStream = AudioSystem.getAudioInputStream(new File(victory_loc).getAbsoluteFile());
	            victory = AudioSystem.getClip();
	            victory.open(audioInputStream);
	            victory.start();
    		 }
    		 catch(Exception ex) {
    			System.out.println("Error with playing sound.");
    			ex.printStackTrace();
    		 }       
    	}
    	else if(hit_ground && !played_sound) {
    		try {
    			played_sound = true;
    			audioInputStream = AudioSystem.getAudioInputStream(new File(lose_loc).getAbsoluteFile());
    			lose = AudioSystem.getClip();
    			lose.open(audioInputStream);
    			lose.start();
    		}
    		catch(Exception ex) {
    			System.out.println("Error with playing sound.");
    			ex.printStackTrace();
	    	}
    	} 
    	else {
    		//System.out.println("nothing!");
    	}
    }
	    
    public void reset_game() {
    	if (replay) {
    		ball_x = initial_ball_x;
    		ball_y = initial_ball_y;
    		ball_z = initial_ball_z;
    	}
    	else {
	    	ball_x = 0;
			ball_y = 0;
			ball_z = 0;
    	}
		played_sound = false;
		//ball_speed = 0.2;
		ball_speed_z = -ball_speed;
		ball_speed_x = ball_speed;
	    ball_speed_y = ball_speed;
	    gravity = 9.8*10000;
	    //ball_x_angle = 0;
	    //ball_y_angle = 0;
	    if (!replay) {
	    	time_speed = time_speed_const;
	    }
		time = 0;
		start_animation = false;
		game_over = false;
		hit_ground = false;
		shot_hit = false;
		replay = false;
		maxed_out = false;
		very_maxed_out = false;
		chance = 0;
    }
    
    public boolean withinTolerance(double obj_pos, double collision_pos, double tolerance) {
    	return Math.abs(obj_pos - collision_pos) < tolerance;
    }

    public boolean detectCollision() {
    	boolean result = false;
    	//System.out.println("ball_z: " + ball_z);
    	//System.out.println("less than: " + (-court_width+backboard_z+2*ball_size));
    	//checking to see if backboard was hit.
    	if (withinTolerance(ball_z, -court_width+backboard_z+ball_size, ball_size) && 						  //checks ball z to backboard z with room for error
    		withinTolerance(ball_y, post_height-f_factor-floor_height, backboard_height+ball_size) && 	  //checks ball y to backboard y range
    		withinTolerance(ball_x, 0, backboard_width+ball_size)) 										  //checks ball x to backboard x range
    	{ 
    		ball_speed_z *= -bounce_length_percentage;
    		ball_z = -court_width+backboard_z+2*ball_size;
    		last_z_collision = -court_width+backboard_z+2*ball_size;
    		result = true;
    		System.out.println("Backboard Collision");
    	}

    	//rim
    	if (withinTolerance(ball_y, rim_y, ball_size)) {
    		//System.out.println("Ball at rim height");
    		for (int i = 0; i < 360; i+=10) {
    			double check_rim_x = rim_size*Math.cos(Math.toDegrees(i));
    			double check_rim_z = -court_width+rim_z+rim_size+rim_size*Math.sin(Math.toDegrees(i));
    			if (withinTolerance(ball_x, check_rim_x, ball_size) &&
    				withinTolerance(ball_z, check_rim_z, ball_size)) {
    				double normal_factor = ball_speed_x*Math.cos(Math.toRadians((i)))+ball_speed_z*Math.sin(Math.toRadians(i));
    				ball_speed_z += -2*bounce_length_percentage*normal_factor*Math.sin(Math.toRadians(i));
    				ball_speed_x += -2*bounce_length_percentage*normal_factor*Math.cos(Math.toRadians(i));
    				result = true;
    				break;
    			}
    		}
    	}
    	//check if the shot was hit.
    	if (withinTolerance(ball_y, rim_y, ball_size)) {
    		if (withinTolerance(ball_x, 0, rim_size) &&
    			withinTolerance(ball_z, -court_width+rim_z+rim_size, rim_size)) {
    			shot_hit = true;
    			System.out.println("Shot hit!");
    		}
    	}
    	    	
    	//checking to see if a fence was hit
    	if (withinTolerance(ball_y, 0, fence_height+ball_size)) {
    		if (withinTolerance(ball_z, court_width, ball_size)) {
    			ball_speed_z *= -bounce_length_percentage;
    			result = true;
    			ball_z = court_width-ball_size;
    			last_z_collision = court_width-ball_size;
    			//System.out.println("Fence Collision 1");
    		}
    		if (withinTolerance(ball_z, -court_width, ball_size)) {
    			ball_speed_z *= -bounce_length_percentage;
    			ball_z = -court_width+ball_size;
    			last_z_collision = -court_width+ball_size;
    			result = true;
    			//System.out.println("Fence Collision 2");
    		}
    		if (withinTolerance(ball_x, court_length, ball_size)) {
    			//System.out.println("Fence Collision 3");
    			ball_speed_x *= -bounce_length_percentage;
    			ball_x = court_length - ball_size;
    			result = true;
    		}
    		if (withinTolerance(ball_x, -court_length, ball_size)) {
    			ball_speed_x *= -bounce_length_percentage;
    			ball_x = -court_length+ball_size;
    			result = true;
    			//System.out.println("Fence Collision 4");
    		}	
    	}
    		
    	//checking to see if the floor was hit (add in more velocity)
    	if (ball_y < -f_factor-floor_height+ball_size) {
    		ball_speed_y *= -bounce_height_percentage;
    		ball_y = -f_factor-floor_height+ball_size;
    		ball_speed_x *= friction;
    		ball_speed_z *= friction;
    		result = true;
    		hit_ground = true;
    		game_over = true;

    		//System.out.println("Floor Collision");
    	}
    	return result;
    }
    
    double rotate_ball = 0;
    
    public void updateLocation() {
    	//ball_speed_y -= gravity;
    	rotate_ball++;
    	if (rotate_ball > 360) {
    		rotate_ball -= 360;
    	}
    	ball_x += ball_speed_x * time_speed;
    	ball_y += ball_speed_y * time_speed;
    	ball_z += ball_speed_z * time_speed;
    	ball_speed_y -= gravity * time_speed;
    	time += time_speed;

    	//System.out.printf("ball_speed_y: %f, time: %f\n", ball_speed_y, time);
    	//System.out.printf("ball_x: %f, ball_y: %f, ball_z: %f\n", ball_x, ball_y, ball_z);
    	if (detectCollision()) {
        	//System.out.printf("Collision occured at ball_x: %f, ball_y: %f, ball_z: %f\n", ball_x, ball_y, ball_z);
        	//System.out.printf("ball_y_speed: %f\n", ball_speed_y);
        	
        	if (withinTolerance(ball_speed_y, 70, 1.0)) {
        		System.out.println("No more bouncing");
        		ball_y = min_bounce_height+ball_size;
        		ball_speed_y = 0;
        		gravity = 0;
        	}
        	
    	}
    	if (gravity == 0) {
    		ball_speed_x *= friction;
    		ball_speed_z *= friction;
    	}
    }
    
    public void drawBall(final GL2 gl) {
    	gl.glPushMatrix();
    	ball.enable(gl);
		ball.bind(gl);
		if (start_animation) {
			updateLocation();
			
			gl.glTranslated(ball_x, ball_y, ball_z);
			gl.glRotated(rotate_ball, 1, 0, 0);
			//gl.glRotated(time, 1, 0, 0);
			//System.out.printf("x: %f, y: %f, z: %f\n", ball_x, ball_y, ball_z);
		}
		GLUquadric bball = glu.gluNewQuadric();
		glu.gluQuadricTexture(bball, true);
		glu.gluQuadricDrawStyle(bball, GLU.GLU_FILL);
		glu.gluQuadricNormals(bball, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(bball, GLU.GLU_OUTSIDE);
		glu.gluSphere(bball, ball_size, 50, 50);
		glu.gluDeleteQuadric(bball);
		ball.disable(gl);
		gl.glTranslated(-ball_x, -ball_y, -ball_z);
		if (start_animation) {
			gl.glRotated(-rotate_ball, 1, 0, 0);
		}
	    gl.glPopMatrix();
    }
    
    public void drawFence(final GL2 gl) {
		gl.glPushMatrix();
	    gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	    fence.enable(gl);
	    fence.bind(gl);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(-court_length,  -f_factor-floor_height, -court_width);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(court_length, -f_factor-floor_height, -court_width);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(court_length,  fence_height, -court_width);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(-court_length, fence_height, -court_width);
	    gl.glEnd();

	    //left
	    gl.glRotatef(90, 0, 1, 0);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(-court_width,  -f_factor-floor_height, -court_length);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(court_width, -f_factor-floor_height, -court_length);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(court_width,  fence_height, -court_length);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(-court_width, fence_height, -court_length);
	    gl.glEnd();
	    //back
	    
	    gl.glRotatef(90, 0, 1, 0);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(-court_length,  -f_factor-floor_height, -court_width);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(court_length, -f_factor-floor_height, -court_width);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(court_length,  fence_height, -court_width);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(-court_length, fence_height, -court_width);
	    gl.glEnd();
	    
	    //right
	    gl.glRotatef(90, 0, 1, 0);
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(-court_width,  -f_factor-floor_height, -court_length);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(court_width, -f_factor-floor_height, -court_length);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(court_width,  fence_height, -court_length);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(-court_width, fence_height, -court_length);
	    gl.glRotatef(90, 0, 1, 0);
	    gl.glEnd();

	    gl.glPopMatrix();
    }

    public void drawAvatar(final GL2 gl) {
    	
    	if(replay) {
    	gl.glPushMatrix();
    	face_texture.enable(gl);
		face_texture.bind(gl);
		
		gl.glTranslatef(0, 2, 5);
	
		gl.glRotatef(180,0,1,0);
		gl.glRotatef(270,1,0,0);
		
		GLUquadric head = glu.gluNewQuadric();
		glu.gluQuadricTexture(head, true);
		glu.gluQuadricDrawStyle(head, GLU.GLU_FILL);
		glu.gluQuadricNormals(head, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(head, GLU.GLU_OUTSIDE);
		glu.gluSphere(head, ball_size * 1.5, 50, 50);
		glu.gluDeleteQuadric(head);
		face_texture.disable(gl);
		
		gl.glTranslatef(0, 0, -4f);
		gl.glRotatef(270, 0, 0, 1);
		
		torso.bind(gl);
		torso.enable(gl);
		GLUquadric body = glu.gluNewQuadric();
		glu.gluQuadricTexture(body, true);
		glu.gluQuadricDrawStyle(body, GLU.GLU_FILL);
		glu.gluQuadricNormals(body, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(body, GLU.GLU_OUTSIDE);
		glu.gluCylinder(body, .7f, 1f, 3f, 100, 100);
		glu.gluDeleteQuadric(body);
		torso.disable(gl); 
		
		gl.glTranslated(0, 1f, -1f);
		//arm
		skin.bind(gl);
		skin.enable(gl);
		GLUquadric arm = glu.gluNewQuadric();
		glu.gluQuadricTexture(arm, true);
		glu.gluQuadricDrawStyle(arm, GLU.GLU_FILL);
		glu.gluQuadricNormals(arm, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(arm, GLU.GLU_OUTSIDE);
		glu.gluCylinder(arm, .1f, .2f, 3.5f, 100, 100);
		gl.glTranslated(0, -2, 0);
		glu.gluCylinder(arm, .1f, .2f, 3.5f, 100, 100);
		
		glu.gluDeleteQuadric(body);
		skin.disable(gl); 
		
		gl.glTranslatef(0, 1f, -3f);
		
		pants.bind(gl);
		pants.enable(gl);
		GLUquadric legs = glu.gluNewQuadric();
		glu.gluQuadricTexture(legs, true);
		glu.gluQuadricDrawStyle(legs, GLU.GLU_FILL);
		glu.gluQuadricNormals(legs, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(legs, GLU.GLU_OUTSIDE);
		glu.gluCylinder(legs, .7f, .7f, 4f, 100, 100);
		glu.gluDeleteQuadric(legs);
		pants.disable(gl); 
	    gl.glPopMatrix();
    	}
    }
    
    public void drawFloor(final GL2 gl) {
    	gl.glPushMatrix();
    	halfcourt.enable(gl);
	    halfcourt.bind(gl);
	    gl.glRotatef(270, 0, 1, 0); //flip court to face right way
	    gl.glRotatef(90, 1, 0, 0); //rotate so looking down at court
	    //gl.glTranslatef(-20, 20, -29);
	    gl.glTranslatef(0, 0, floor_height); //bring the court up
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(-court_width, -court_length, f_factor);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(court_width, -court_length, f_factor);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(court_width, court_length, f_factor);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(-court_width, court_length, f_factor);
	    gl.glEnd();
	    gl.glRotatef(-90, 1, 0, 0);
	    gl.glRotatef(-270, 0, 1, 0);
	    halfcourt.disable(gl); 
	    gl.glPopMatrix();
    }

    public void drawBackBoard(final GL2 gl) {
    	gl.glPushMatrix();
    	backboard.enable(gl);
	    backboard.bind(gl);
      	//gl.glTranslatef(-5, 0, 25); 
      	gl.glTranslated(0, post_height-f_factor-floor_height, -court_width+backboard_z);
      	gl.glRotatef(180, 1, 0, 0);
      	//gl.glRotatef(90, 0, 1, 0);
        gl.glBegin(GL2.GL_QUADS);
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(-backboard_width,  -backboard_height, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(backboard_width, -backboard_height, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(backboard_width,  backboard_height, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(-backboard_width, backboard_height, 0);
	    gl.glEnd();
	    backboard.disable(gl);
	    gl.glPopMatrix();
    }
	
    public void drawPost(final GL2 gl) {
    	gl.glPushMatrix();
		metal.bind(gl);
		metal.enable(gl);
		gl.glTranslatef(0, -f_factor-floor_height, -court_width+post_z);
		gl.glRotated(270, 1, 0, 0);
		GLUquadric pole = glu.gluNewQuadric();
		glu.gluQuadricTexture(pole, true);
		glu.gluQuadricDrawStyle(pole, GLU.GLU_FILL);
		glu.gluQuadricNormals(pole, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(pole, GLU.GLU_OUTSIDE);
		glu.gluCylinder(pole, post_radius, post_radius, post_height, 100, 100);
		glu.gluDeleteQuadric(pole);
		metal.disable(gl); 
		gl.glPopMatrix();
    }
    
    public void drawRim(final GL2 gl) {
    	gl.glPushMatrix();
    	float f_rim_size = (float) rim_size;
		metal.bind(gl);
		metal.enable(gl);
		gl.glTranslatef(0, rim_y, -court_width+rim_z+f_rim_size);
		gl.glRotated(90, 1, 0, 0);
		glut.glutSolidTorus(rim_inner_radius, rim_size, 100, 100);
		metal.disable(gl);
		gl.glPopMatrix();
    }
    
    public void drawGoal(final GL2 gl) {
    	//drawPost(gl);
    	drawBackBoard(gl);
	    drawRim(gl);
    }
    
    public void drawAimingArrows(final GL2 gl) {
    	gl.glPushMatrix();
    	arrow.enable(gl);
	    arrow.bind(gl);
	    gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glTranslatef(arrow_pos_x, arrow_pos_y, hud_factor+1); //set initial pos
		
		//draw aiming arrows
	    //right arrow
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, arrow_dim, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(arrow_dim, arrow_dim, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(arrow_dim, 0, 0);
	    gl.glEnd();
	    
	    //up arrow
	    gl.glRotatef(90, 0, 0, 1);
	   
	    gl.glTranslatef(.7f, -.5f, 0);
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, arrow_dim, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(arrow_dim, arrow_dim, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(arrow_dim, 0, 0);
	    gl.glEnd();
	    
	  //left arrow
	    gl.glRotatef(90, 0, 0, 1);
	   
	    gl.glTranslatef(.7f, -.5f, 0);
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, arrow_dim, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(arrow_dim, arrow_dim, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(arrow_dim, 0, 0);
	    gl.glEnd();
	    
	    //down arrow
	    gl.glRotatef(90, 0, 0, 1);
	   
	    gl.glTranslatef(.7f, -.5f, 0);
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, arrow_dim, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(arrow_dim, arrow_dim, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(arrow_dim, 0, 0);
	    gl.glEnd();
	    arrow.disable(gl); 
    }
    
    public void drawPosArrows(final GL2 gl) {
    	gl.glPushMatrix();
    	
    	lr_arrow.enable(gl);
 	    lr_arrow.bind(gl);
 	    
 	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	  	
 	    gl.glTranslatef(lr_arrow_pos_x, lr_arrow_pos_y, 0);
 	    
 	    //draw positional arrows
 	    gl.glBegin(GL2.GL_QUADS); 
 	    gl.glTexCoord2f(1, 1); gl.glVertex3f(-lr_arrow_dim, -lr_arrow_dim, lr_arrow_dim);
 	    gl.glTexCoord2f(0, 1); gl.glVertex3f(lr_arrow_dim, -lr_arrow_dim, lr_arrow_dim);
 	    gl.glTexCoord2f(0, 0); gl.glVertex3f(lr_arrow_dim, lr_arrow_dim, lr_arrow_dim);
 	    gl.glTexCoord2f(1, 0); gl.glVertex3f(-lr_arrow_dim, lr_arrow_dim, lr_arrow_dim);
 	    gl.glEnd();
 	    
 	    lr_arrow.disable(gl); 
 	    gl.glPopMatrix();
    }
   
    public void drawForceGauge(final GL2 gl) {
    	//draw rectangle "holder"   	
    	gl.glTranslatef(gauge_pos_x, gauge_pos_y, hud_factor+2);
    	
    	gl.glPushMatrix();
    	gauge.enable(gl);
	    gauge.bind(gl);
	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	  	
	    //draw back of gauge
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, gauge_h, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(max_gauge_w+1.15f, gauge_h, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(max_gauge_w+1.15f, 0, 0);
	    gl.glEnd();
	    
	    gauge.disable(gl);  
	    water.enable(gl);
	    water.bind(gl);
	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

	    gl.glTranslatef(.5f, .55f, 1);
	    //draw gauge
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, gauge_h/2f, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(gauge_width, gauge_h/2f, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(gauge_width, 0, 0);
	    gl.glEnd();
	    
	    water.disable(gl);
	    
	    gl.glTranslatef(0, 0, 5);
	    
	    hud_top.enable(gl);
	    hud_top.bind(gl);
	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	  	
	    //draw back of gauge
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, gauge_h, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(max_gauge_w+1.15f, gauge_h, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(max_gauge_w+1.15f, 0, 0);
	    gl.glEnd();
	    
	    hud_top.disable(gl);
	    gl.glPopMatrix();
    }
   
    public void drawCommand(final GL2 gl) {
    	//draw rectangle "holder"
    	gl.glTranslatef(-7f, -5f, hud_factor-1);    	
    	gl.glPushMatrix();
    	
    	command.enable(gl);
	    command.bind(gl);
	    
	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

	    float r_w = 14f;
	    float r_h = 3.75f;
	    //draw back of gauge
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, r_h, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(r_w, r_h, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(r_w, 0, 0);
	    gl.glEnd();
	    
	    command.disable(gl);
	    
	    gl.glPopMatrix();
    }
    
    public void drawReplayHud(final GL2 gl) {
    	//draw rectangle "holder"
    	gl.glTranslatef(1.3f, 3.5f, hud_factor);    	
    	gl.glPushMatrix();
    	
    	r_hud.enable(gl);
	    r_hud.bind(gl);
	    
	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	  	    
	    
	    float r_w = 5f;
	    float r_h = 1f;

	    //draw back of gauge
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, r_h, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(r_w, r_h, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(r_w, 0, 0);
	    gl.glEnd();
	    
	    r_hud.disable(gl);
	    
	    gl.glPopMatrix();
    }
    
    public void title_screen(final GL2 gl) {
    	//draw rectangle "holder"
    	gl.glTranslatef(-6.5f, -4.6f, hud_factor);    	
    	gl.glPushMatrix();
    	
    	title.enable(gl);
	    title.bind(gl);
	    
	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	  	    
	    
	    float r_w = 13f;
	    float r_h = 9.5f;

	    //draw back of gauge
	    gl.glBegin(GL2.GL_QUADS); 
	    gl.glTexCoord2f(0, 0); gl.glVertex3f(0, 0, 0);
	    gl.glTexCoord2f(0, 1); gl.glVertex3f(0, r_h, 0);
	    gl.glTexCoord2f(1, 1); gl.glVertex3f(r_w, r_h, 0);
	    gl.glTexCoord2f(1, 0); gl.glVertex3f(r_w, 0, 0);
	    gl.glEnd();
	    
	    title.disable(gl);
	    
	    gl.glPopMatrix();
    }
    
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		if(!start_game) {
			title_screen(gl);
		}
		
		else {
			bg.stop();
			bg2.loop(Clip.LOOP_CONTINUOUSLY);
			
			gl.glPushMatrix();
			glu.gluLookAt(0, 0, 1, 0, -y/100, 0, 0, 1, 0);
			gl.glPushMatrix();
			gl.glRotatef(x, 0, 1, 0);
			//gl.glRotatef(y, 1, 0, 0);
			// Enable/Disable features
			gl.glPushAttrib(GL2.GL_ENABLE_BIT);
			gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glDisable(GL2.GL_DEPTH_TEST);
			gl.glDisable(GL2.GL_LIGHTING);
			gl.glDisable(GL2.GL_BLEND);
	
			drawSkybox(gl);
		    gl.glTranslatef(mv_right, 0, mv_forward);
		    gl.glPopAttrib();
		    //sets starting position
		    gl.glTranslatef(0, 0, center_z);
	
		    drawFloor(gl);
			//drawFence(gl);
		    drawGoal(gl);
		    drawAvatar(gl);
			drawFence(gl);
	
		    if (start_animation) {
		    	drawBall(gl);
		    	if(shot_hit || hit_ground) {
		    		play_sound();
		    	}
		    }
	
			gl.glPopMatrix();
			if (!start_animation) {
				drawBall(gl);
			}
	
		    gl.glPopMatrix();
		    gl.glPopMatrix();
		    
		    if (!replay && !start_animation) {
		    	
				drawCommand(gl);
				gl.glPopMatrix();
				drawAimingArrows(gl);
			    gl.glPopMatrix();
			    drawPosArrows(gl);
			    gl.glPopMatrix();
			    drawForceGauge(gl); 
		    }
		    else { 
			    gl.glPopMatrix();
		    	drawReplayHud(gl);
		    }
		}
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {		
	}
	
	boolean object_clicked(float size_x, float size_y, float object_x, float object_y, float curr_x, float curr_y) {
    	if((curr_x > object_x-size_x) && (curr_x < (object_x + size_x)) && (curr_y < object_y+size_y) && (curr_y > (object_y - size_y))) {
    		return true;
    	}
    	return false;
    }

	public void keyTyped(KeyEvent e) {
		key= e.getKeyChar();
		System.out.printf("Key typed: %c\n", key);
		//System.out.printf("ball_x: %f, ball_y: %f, ball_z: %f\n", ball_x, ball_y, ball_z);
		switch (key) {
		case 'z':
			start_game = true;
			break;
		case 'w':
			if (replay) {
				mv_forward++;
				if (!start_animation) {
					ball_z--;
				}
			}
			break;
		case 's':
			if (replay) {
				mv_forward--;
				if (!start_animation) {
					ball_z++;
				}
			}
			break;
		case 'a':
			if (mv_right < max_left_right || replay) {
				mv_right++;
				if (!start_animation) {
					ball_x--;
				}
			}
			break;
		case 'd':
			if (mv_right > -max_left_right || replay) {
				mv_right--;
				if (!start_animation) {
					ball_x++;
				}
			}
			break;
		case 'r':
			replay = false;
			mv_right = 0;
			mv_forward = 0;
			hit_ground = false;
			shot_hit = false;
			reset_game();
			x = 0;
			y = 0;
			break;
		case '0':
			//TODO: boolean variable to say whether you can replay or not. (set after shot finished.)
			if (hit_ground || shot_hit || replay) {
				replay = true;
				reset_game();
				replay = true;
				ball_speed_x = initial_ball_speed_x;
				ball_speed_y = initial_ball_speed_y;
				ball_speed_z = initial_ball_speed_z;
				start_animation = true;
			}
			break;
		case '1':
			if (replay) {
				time_speed = time_speed_const;
			}
			break;
		case '2':
			if (replay) {
				time_speed = time_speed_const / 4;
			}
			break;
		case '3':
			if (replay) {
				time_speed = time_speed_const / 10;
			}
			break;
		
		default:
			break;
		}
	}

	public void keyPressed(KeyEvent e) {
		key = e.getKeyChar();
		
		if(key == ' ') {
			if(gauge_width < max_gauge_w) {
				gauge_width = gauge_width+.1f;
			}
			else {
				maxed_out = true;
				System.out.println("maxed out!!");
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		char code = e.getKeyChar();
		if (!start_animation) {
			if (code == KeyEvent.VK_SPACE && !maxed_out) {
				System.out.println("Starting animation");
				start_animation = true;
				ball_x_angle = x;
				ball_y_angle = y;
				ball_speed = gauge_width*ball_speed_mult + ball_speed_add;
				double helper_angle = -30;
				if (ball_y_angle > 0) {
					helper_angle *= -1;
				}
				
				ball_speed_x = ball_speed*Math.sin(Math.toRadians(ball_x_angle))*Math.cos(Math.toRadians(ball_y_angle));
		    	ball_speed_y = -ball_speed*Math.sin(Math.toRadians(ball_y_angle+helper_angle));
		    	ball_speed_z = -ball_speed*Math.cos(Math.toRadians(ball_x_angle))*Math.cos(Math.toRadians(ball_y_angle));
		    	initial_ball_speed_z = ball_speed_z;
		    	initial_ball_speed_y = ball_speed_y;
		    	initial_ball_speed_x = ball_speed_x;
		    	initial_ball_x = ball_x;
		    	initial_ball_y = ball_y;
		    	initial_ball_z = ball_z;
		    	//System.out.printf("ball_speed_x: %f, ball_speed_y: %f, ball_speed_z: %f\n", ball_speed_x, ball_speed_y, ball_speed_z);
			}
		}
		gauge_width = 0;
		if(maxed_out) {
			chance++;
		}
		maxed_out = false;
		if(chance == 2) {
			hit_ground = true;
			play_sound();
			reset_game();
		}
		System.out.println("chances: " + chance);
	}

	public void mouseDragged(MouseEvent e) {
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		
		float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
		float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
		
		if(mouseX1 > XX) {
			right = true;
		}
		else if (mouseX1 < XX) {
			left = true;
		}
		else if (mouseY1 > YY) {
			up = true;
		}
		else if(mouseY1 < YY) {
			down = true;
		}
		mouseX1 = XX;
		mouseY1 = YY;
		
		if(SwingUtilities.isRightMouseButton(e)) {
			r_click = true;
			l_click = false;
			if(up) {
				y++;
			}
			else if(down) {
				y--;			
			}
		}
		else if(SwingUtilities.isLeftMouseButton(e)) {
			l_click = true;
			r_click = false;
			if(right) {
				x++;
			}
			else if(left) {
				x--;
			}
		}	
		event = true;
	}
		
	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {			
	}

	public void mousePressed(MouseEvent e) {
		float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
		float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
		System.out.printf("Point clicked: (%.3f, %.3f)\n", XX, YY);
		mouseX0 = XX;
		mouseY0 = YY;
		//up arrow
		if(object_clicked(arrow_dim, arrow_dim, -11.5f, -7.5f, XX, YY)) {
			System.out.println("clicked upper arrow");
			y--;
		}
		
		//right arrow 
		if(object_clicked(arrow_dim, arrow_dim, -10f, -9.5f, XX, YY)) {
			System.out.println("clicked right arrow");
			x++;
		}
		
		//down arrow 
		if(object_clicked(arrow_dim, arrow_dim, -11.5f, -11f, XX, YY)) {
			System.out.println("clicked lower arrow");
			y++;
		}
		
		//left arrow 
		if(object_clicked(arrow_dim, arrow_dim, -13.5f, -9.5f, XX, YY)) {
			System.out.println("clicked left arrow");
			x--;
		}
		
		//lr left arrow 
		if(object_clicked(lr_arrow_dim, lr_arrow_dim, 10f, -9f, XX, YY)) {
			System.out.println("move left!");
			if (mv_right < max_left_right) {
				mv_right++;
				ball_x--;
			}
		}
		
		//lr right arrow 
		if(object_clicked(lr_arrow_dim, lr_arrow_dim, 13.5f, -9f, XX, YY)) {
			System.out.println("move right!");
			if (mv_right > -max_left_right) {
				mv_right--;
				ball_x++;
			}
		}
		
	}

	public void mouseReleased(MouseEvent e) {
		event = false;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
