
/**
 * The physics model.
 *
 * This class is where you should implement your bouncing balls model.
 *
 * The code has intentionally been kept as simple as possible, but if you wish, you can improve the design.
 *
 * @author Simon Robillard
 *
 */


class Model {

    double areaWidth, areaHeight;

    Ball [] balls;
    double gravity = 9.82;

    Model(double width, double height) {
        areaWidth = width;
        areaHeight = height;

        // Initialize the model with a few balls
        balls = new Ball[2];
        balls[0] = new Ball(width / 3, height * 0.7, -2, 2, 0.2, 100);
        balls[1] = new Ball(2*width / 3, height * 0.7, -2, 2, 0.3, 300);




    }

    public int num = 0;

    void step(double deltaT) {
        // TODO this method implements one step of simulation with a step deltaT

        for (Ball b : balls) {


            if(num != 0) {
                num--;
            }
            b.pos.x += deltaT * b.vel.x;
            b.pos.y += deltaT * b.vel.y;

            // detect collision with the border
            if (b.pos.x < b.radius || b.pos.x > areaWidth - b.radius) {
                b.vel.x *= -1; // change direction of ball
            }
            if (b.pos.y < b.radius || b.pos.y > areaHeight - b.radius) {
                b.vel.y *= -1;
            }else{
                b.vel.y -= deltaT*gravity;
            }

            checkCollision(b);







        }
    }

    void checkCollision(Ball b){
        for (Ball b2 : balls){
            if(!b.equalTo(b2)){

                collision(b,b2);
            }
        }
    }






    void collision(Ball b1, Ball b2) {
        double deltaX = b1.pos.x - b2.pos.x; //skillnaden mellan bollarnas x-koordinater
        double deltaY = b1.pos.y - b2.pos.y; //skillnaden mellan bollarnas y-koordinater

        double collisionDistance = b1.radius + b2.radius; //avståndet mellan x-koordinaterna/y-koordinaterna

        if (deltaX * deltaX + deltaY * deltaY < collisionDistance * collisionDistance && !(b1.collided) && !(b2.collided)) {
           // collidedPreviously = true;
            b1.collided = true;
            b2.collided = true;
            //System.out.println("Momentum before B1: " + balls[0].w * balls[0].vel.r);
            //System.out.println("Momentum before B2: " + balls[1].w * balls[1].vel.r);

            System.out.println("KROCK");

            double rotAngle = Math.atan(deltaY / deltaX); //vilken grad som ska roteras

            b1.vel.rotate(-rotAngle); //egentligen b1V och b2V
            b2.vel.rotate(-rotAngle);

            double Ix = b1.w * b1.vel.x + b2.w * b2.vel.x;
            double Rx = -(b2.vel.x - b1.vel.x);

            b1.vel.x = (Ix - Rx * b2.w) / (b1.w + b2.w);
            b2.vel.x = Rx + b1.vel.x;

            double Iy = b1.w * b1.vel.y + b2.w * b2.vel.y;
            double Ry = -(b2.vel.y - b1.vel.y);

            b1.vel.y = (Iy - Ry * b2.w) / (b1.w + b2.w);
            b2.vel.y = Ry + b1.vel.y;

            b1.vel.rotate(rotAngle);
            b2.vel.rotate(rotAngle);

            //System.out.println("Momentum after B1: " + balls[0].w * balls[0].vel.r );
            //System.out.println("Momentum after B2: " + balls[1].w * balls[1].vel.r );
            
        } else {
            b1.collided = false;
            b2.collided = false;
        }
    }


    class Point {         // this class holds a 2D-vector, eg. position or speed
        double x, y, r, angle;   // vector in rectangular and/or polar coordinates

        Point(double x, double y){
            this.x = x;
            this.y = y;
        }

        void rectToPolar() {
            r = Math.sqrt(x * x + y * y);
            angle = Math.atan(y / x);
            if (x < 0) angle += Math.PI;
        }

        void polarToRect() {
            x = r * Math.cos(angle);
            y = r * Math.sin(angle);
        }

        void rotate(double rotateAngle) {
            rectToPolar();
            angle += rotateAngle;
            polarToRect();
        }

        public boolean equalTo(Point p){
            return this.x == p.x && this.y == p.y;
        }
    }


        /**
     * Simple inner class describing balls.
     */
    class Ball {

        Point pos = null;
        Point vel = null;

        Ball(double x, double y, double vx, double vy, double r, double w) {
            this.pos = new Point(x,y);
            this.vel = new Point(vx,vy);
            this.radius = r;
            this.w = w;
        }

        public boolean equalTo(Ball obj) {
            return this.pos.equalTo(obj.pos) && this.vel.equalTo(obj.vel) && this.radius == obj.radius && this.w == obj.w;
        }



        /**
         * Position, speed, and radius of the ball. You may wish to add other attributes.
         */
        double x, y, vx, vy, radius, w;
        boolean collided;
    }
}