#version 410 compatibility

uniform float rotation;

void main(){
    mat4 ModelView = gl_ModelViewMatrix;

    // Column 0:
   ModelView[0][0] = cos(rotation);
   //ModelView[0][1] = 0;
   //ModelView[0][2] = ;

    // Column 1:
   //ModelView[1][0] *= 0;
   //ModelView[1][1] *= 1;
   //ModelView[1][2] *= 0;

    // Column 2:
   //ModelView[2][0] *= 0;
   //ModelView[2][1] *= 0;
   //ModelView[2][2] *= 1;

    gl_Position = gl_ProjectionMatrix * ModelView * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
}