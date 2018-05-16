
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

    Model(double width, double height) {
        areaWidth = width;
        areaHeight = height;

        // Initialize the model with a few balls
        balls = new Ball[4];
        balls[0] = new Ball(width / 3, height * 0.7, -1.6, 1.2, 0.2, 100);
        balls[1] = new Ball(2*width / 3, height * 0.7, -0.6, 0.6, 0.4, 1000);

    }

    void step(double deltaT) {
        // TODO this method implements one step of simulation with a step deltaT
        for (Ball b : balls) {

            // detect collision with the border
            if (b.x < b.radius || b.x > areaWidth - b.radius) {
                b.vx *= -1; // change direction of ball
            }
            if (b.y < b.radius || b.y > areaHeight - b.radius) {
                b.vy *= -1;
            }

            // compute new position according to the speed of the ball
            checkCollision(b);
            freeMoment(b, deltaT);
        }
    }

    void checkCollision(Ball b){
        for (Ball b2 : balls){
            if(!b.equalTo(b2)){
                ballsCollide(b,b2);
            }
        }
    }
    public int num = 0;
    void ballsCollide(Ball b1, Ball b2){
        double radius;

        /*if(b1.x+ 2*b1.radius == b2.x && b1.y == b2.y){
            b1.vx *= -1;
            b1.vy *= -1;

            b2.vx *= -1;
            b2.vy *= -1;
            System.out.println("hej");
        }*/

        double limit = 0.0005;
        double b1Right = b1.x + b1.radius;
        double b2Left = b2.x - b2.radius;
        double b2Right = b2.x + b2.radius;
        double b1Left = b1.x - b1.radius;

        double b1Top = b1.y - b1.radius;
        double b2Bottom = b2.y + b2.radius;
        double b2Top = b2.y - b2.radius;
        double b1Bottom = b1.y + b1.radius;

        boolean xColl = (b2Left-b1Right <= limit && b1Left-b2Right <= limit);
        boolean yColl = (b1Top-b2Bottom <= limit && b2Top-b1Bottom <= limit);

        if(xColl &&  yColl){
            System.out.println("KROCK");

            double b1V = Math.sqrt(Math.pow(b1.x,2) + Math.pow(b1.y,2));
            double b2V = Math.sqrt(Math.pow(b2.x,2) + Math.pow(b2.y,2));
            double b1Angle = Math.atan(b1.x/b1.y);
            double b2Angle = Math.atan(b2.x/b2.y);
            double I = b1.w * b1V + b2.w * b2V;
            double R = -(b2V-b1V);
            double b1V2 = (I-b2.w*R)/(b2.w+b1.w);
            double b2V2 = R - b1V2;

            System.out.println("B1 VEL: " + b1V2);
            System.out.println("B2 VEL: " + b2V2);
            b1.vx = b1V2 * Math.cos(b1Angle + Math.PI);
            b1.vy = b1V2 * Math.sin(b1Angle + Math.PI);
            b2.vx = b2V2 * Math.cos(b2Angle + Math.PI);
            b2.vy = b2V2 * Math.sin(b2Angle + Math.PI);
        }



    }

    void freeMoment(Ball b, double deltaT){

        double gravity = 9.82;

        if(b.y > 0){
            b.vy -= gravity/b.w;
        }

        b.x += deltaT * b.vx;
        b.y += deltaT * b.vy;
    }

    /**
     * Simple inner class describing balls.
     */
    class Ball {

        Ball(double x, double y, double vx, double vy, double r, double w) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.radius = r;
            this.w = w;
        }

        public boolean equalTo(Ball obj) {
            return this.x == obj.x && this.y == obj.y && this.vx == obj.vx && this.vy == obj.vy && this.radius == obj.radius && this.w == obj.w;
        }
        /**
         * Position, speed, and radius of the ball. You may wish to add other attributes.
         */
        double x, y, vx, vy, radius, w;
    }
}