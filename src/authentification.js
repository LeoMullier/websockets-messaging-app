

export default class Rectangle {
    constructor(hauteur, largeur) {
      this.hauteur = hauteur;
      this.largeur = largeur;
    }
  
    get area() {
      return this.calcArea();
    }
  
    calcArea() {
      return this.largeur * this.hauteur;
    }
  }
  

