public class Body{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    /* public final double G = 6.67 * Math.pow(10, -11); */
    public final double G = 6.67e-11;

    /*  Constructor 1 */
    public Body(double xP, double yP, double xV,
                double yV, double m, String img)
    {xxPos = xP ; yyPos = yP; xxVel = xV;
     yyVel = yV ; mass = m; imgFileName = img;
    }

    /* Constructor 2
     calculates the distance between two Bodys */
    public Body(Body b)
    {xxPos = b.xxPos; yyPos = b.yyPos; xxVel = b.xxVel;
     yyVel = b.yyVel; mass = b.mass; imgFileName = b.imgFileName;
    }

     /* calcDistance */
    public double calcDistance(Body body)
    {
        double dx = Math.abs(this.xxPos - body.xxPos);
        double dy = Math.abs(this.yyPos - body.yyPos);
        double distance = Math.sqrt(
                dx * dx + dy * dy );
        return distance;
    }

    /* calcForceExertedBy
    * returns a double describing the force
    * exerted on this body by the given body
    * samh.calcForceExertedBy(rocinante) */
    public double calcForceExertedBy(Body body)
    {
        double r = this.calcDistance(body);
        double F = (G * mass * body.mass) / r * r ;
        return  F;
    }

    /* calcForceExertedByX */
    public double calcForceExertedByX(Body body)
    {
        double dx = Math.abs(this.xxPos - body.xxPos);
        if (this.xxPos < body.xxPos)       { }
        else if (this.xxPos == body.xxPos) {return 0;}
        else                               {dx = -dx ;}

        double F  = this.calcForceExertedBy(body);
        double r  = this.calcDistance(body);
        double Fx = (F * dx) / r ;
        return Fx;
    }

    /* calcForceExertedByY */
    public double calcForceExertedByY(Body body)
    {
        double dy = Math.abs(this.yyPos - body.yyPos);
        if (this.yyPos < body.yyPos)       { }
        else if (this.yyPos == body.yyPos) {return 0;}
        else                               {dy = -dy ;}

        double F  = this.calcForceExertedBy(body);
        double r  = this.calcDistance(body);
        double Fy = (F * dy) / r ;
        return Fy;
    }

    /* calcNetForceExertedByX */
    public double calcNetForceExertedByX(Body [] body_array)
    {
        int len = body_array.length - 1;
        int i   = 0;
        double allX = 0;
        while (i < len)
        {
            if (!this.equals(body_array[i]))
            {
                allX += this.calcForceExertedByX(body_array[i]);
            }
            i += 1;
        }
        return allX;
    }

    /* calcNetForceExertedByY */
    public double calcNetForceExertedByY(Body [] body_array)
    {
        int len = body_array.length - 1;
        int i   = 0;
        double allY = 0;
        while (i < len)
        {
            if (!this.equals(body_array[i]))
            {
                allY += this.calcForceExertedByY(body_array[i]);
            }
            i += 1;
        }
        return allY;
    }


    public void update(double dt, double fX, double fY)
    {
        /* new acceleration */
        double ax = fX / this.mass;
        double ay = fY / this.mass;
        /* new velocity */
        this.xxVel += dt * ax;
        this.yyVel += dt * ay;
        /* new position */
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;

    }
    public void draw()
    {
        StdDraw.picture(xxPos,yyPos,imgFileName);
    }


}











