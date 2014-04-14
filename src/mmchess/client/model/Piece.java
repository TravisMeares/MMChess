/*
 * Copyright 2014 Matthew.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mmchess.client.model;

/**
 *
 * @author Matthew
 */
abstract class Piece {
    
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final Piece EMPTY = null;
    
    public Piece(int xpos, int ypos, int color) {
        this.color = color;
        this.xpos = xpos;
        this.ypos = ypos;
    }
    
    public abstract Move[] getMoves();
    
    public int getXpos() { return xpos; }
    public int getYpos() { return ypos; }
    public int getColor() { return color; }
    public void setYpos(int ypos) { this.ypos = ypos; }
    public void setXpos(int xpos) { this.xpos = xpos; }
    
    private int xpos;
    private int ypos;
    private final int color;
    
    
}