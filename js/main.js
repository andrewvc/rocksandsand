function relMouseCoords(event) {
    // Thanks to: http://stackoverflow.com/questions/55677/how-do-i-get-the-coordinates-of-a-mouse-click-on-a-canvas-element
    var totalOffsetX = 0;
    var totalOffsetY = 0;
    var canvasX = 0;
    var canvasY = 0;
    var currentElement = this;

    do {
        totalOffsetX += currentElement.offsetLeft;
        totalOffsetY += currentElement.offsetTop;
    }
    while (currentElement = currentElement.offsetParent)

    canvasX = event.pageX - totalOffsetX;
    canvasY = event.pageY - totalOffsetY;

    return {
        x: canvasX,
        y: canvasY
    }
}
HTMLCanvasElement.prototype.relMouseCoords = relMouseCoords;

function distance(start, end) {
  return Math.sqrt(Math.pow(end[0] - start[0], 2) + Math.pow(end[1] - start[1],2));
}

function midpoint(start, end) {
  return [
    (start[0] + end[0] / 2),
    (start[1] + end[1] / 2)
  ];
}


function Pen (garden) {
  this.garden = garden
  var ctx = garden.ctx;
   
  this.state = 'up';
  this.rakeState  =  'available';
  this.x = 0;
  this.y = 0;
  this.setCoords = function (coords) {
    this.x = coords.x;
    this.y = coords.y;
  };
  this.startSampler = function () {
    var self = this;
    setInterval(function () {
      if (self.state === 'down') {
        self.rakeState = 'available';
      }
    }, 10);  
  };
  this.lastTinePosition = null;
  this.reset = function () {
    console.log("Resetting " + this);
    this.state = 'up';
    this.lastTinePositions = null;
  };
  this.brushSize = 30;
  this.tineCount = 5;
}

Pen.prototype.rakeTo = function (endX,endY) {
  var pen = this;
  var ctx = this.garden.ctx;
  var slope = (this.y - endY) / (this.x - endX);
  var pSlope = -(1 / slope); //Perpendicular slope
  var brushSize = this.brushSize;
  var tineCount = 5;
  var brushSpacing = brushSize / tineCount;

  ymod = pSlope <= 1 ? pSlope : 1;
  ymod = ymod >= -1 ? ymod : -1;

  if (slope < 0) {
    ymod = Math.abs(ymod);
  }
 
  xmod = slope <= 1 ? slope : 1;
  xmod = xmod >= -1 ? xmod : -1;
   
  if (slope < 0) {
    xmod = Math.abs(xmod);
  }
  

  // Flip the tines over periodically so as to prevent path crossover
  // during rake flips
  var tinePositions = [];
  var tineIdxs = (slope < 0) ? _.range(0, tineCount) : _.range(tineCount-1, -1, -1);
  _.each(tineIdxs, function(i) {
    var xOffset = (brushSpacing * (i + 1) * xmod) + (-(brushSize * xmod / 2));
    var yOffset = (brushSpacing * (i + 1) * ymod) + (-(brushSize * ymod / 2));
    tinePositions[i] = [endX + xOffset, endY + yOffset];
  });

  // If this is the start, we have no origin
  if (!pen.lastTinePositions) {
    pen.lastTinePositions = tinePositions;
  }

  // Detect rake flips, and when found draw the first iteration linking
  // the tines in reverse

  // TODO: This can be detected smarter
  var justFlipped = (
      distance(pen.lastTinePositions[tineCount-1], tinePositions[0])
      <
      distance(pen.lastTinePositions[0], tinePositions[0]));

  var lastTineIdxMap = justFlipped ? _.range(tineCount-1, -1, -1) : _.range(0, tineCount);
  _.each(tinePositions, function (tinePos,i) {
    var lastTineIdx = lastTineIdxMap[i]
    var lastTinePos = pen.lastTinePositions[lastTineIdx];

    ctx.beginPath();
    ctx.strokeStyle = "fff";
    ctx.moveTo(lastTinePos[0], lastTinePos[1]);

    // surrounding grains
    _.times(30, function () {
      ctx.fillStyle = 'rgba(100,100,100,' + (Math.random() - 0.65) + ')';
      var x = (lastTinePos[0] + tinePos[0]) / 2;
      var y = (lastTinePos[1] + lastTinePos[1]) / 2;
      var offBy = (Math.random() * 6) - 4;
      var size = Math.random() * 1.7;
      ctx.fillRect(x + offBy, y + offBy, size,size);
    });

    // Inner white
    ctx.lineWidth = (Math.random()*1.7) + 1.8;
    ctx.lineTo(tinePos[0], tinePos[1]);
    ctx.stroke();
  });

  pen.lastTinePositions = tinePositions;
  
  pen.rakeState = 'unavailable';
  pen.x = endX;
  pen.y = endY;
}

function Garden (selector) {
  this.$el = $(selector);
  this.el  = this.$el[0];
  this.ctx = this.el.getContext('2d');
  this.pen = new Pen(this);
  this.dumpSand = function () {
    w = this.el.width;
    h = this.el.height;
    
    var grainCount = (w * h) * 2;
    for (var i=0; i < grainCount; i++) {
      var x = Math.random() * w;
      var y = Math.random() * h;
      var size = Math.random() / 1.5;
      this.ctx.fillStyle = "rgba(190,190,190," + (Math.random()) + ")";
      this.ctx.fillRect(x,y, size, size); 
    }
  }
}

function Rock (url, width) {
  var self = this;
  this.img = $('<img>')[0];

  this.$canvas = $('<canvas></canvas>');
  this.canvas  = this.$canvas[0];

  this.$canvas.addClass('rock');

  this.img.onload = function () {
    //self.canvas.draggable = true;
    var aspect = self.img.height / self.img.width;
    self.canvas.width  = width;
    self.canvas.height = width * aspect;
    self.ctx           = self.canvas.getContext('2d');
    self.ctx.drawImage(self.img, 0, 0, self.canvas.width, self.canvas.height);
    self.$canvas.draggable({
      drop: function (e,ui) {
        console.log("HAI!");

      }
    });
     
    if (self.onReady) {
      self.onReady();
    }
  };

  this.img.src = url;

  this.onReady = function () {};
}

window.garden = new Garden('#garden');

garden.$el.bind('dragover', function (e) {
  e.preventDefault();
});

garden.$el.droppable({
  drop: function (e,ui) {
    var relTop  = ui.helper.offset().top  - garden.$el.offset().top;
    var relLeft = ui.helper.offset().left - garden.$el.offset().left;
    console.log("Placing stone", relTop, relLeft);
    console.log(garden.$el.offset().top, ui.helper.offset().top);
    var rockCanvas = ui.helper[0];
    garden.ctx.moveTo(0,0);
    garden.ctx.drawImage(rockCanvas,relLeft,relTop,rockCanvas.width,rockCanvas.height);
    ui.helper.hide();
    //  console.log('drop',ui.offset.left,ui.offset.top);
  }
});

garden.$el.mousedown(function (e) {
    var coords = garden.el.relMouseCoords(e);
    garden.pen.state = 'down';
    garden.pen.setCoords(coords);
});

garden.$el.mouseup(function (e) {
  garden.pen.reset();
});

garden.$el.mouseout(function (e) {
  garden.pen.reset();
});

garden.$el.mousemove(function (e) {
  if (garden.pen.state === 'down') {
    var coords = garden.el.relMouseCoords(e);
    if (distance([garden.pen.x, garden.pen.y], [coords.x, coords.y]) > 4) {
      garden.pen.rakeTo(coords.x, coords.y);
    }
  }
});

$(function () {
  garden.dumpSand();
  garden.pen.startSampler();
  garden.rocks = [];
  // Filename of the rock + its width
  // it will be proportionally resized
  var rocksMeta = [
    ['0.png', 130],
    ['1.png', 80],
    ['2.png', 60],
    ['3.png', 120],
  ];
  _.each(rocksMeta, function (rockMeta) {
    var fn    = rockMeta[0];
    var width = rockMeta[1];
    var rock  = new Rock('/images/rocks/' + fn, width);
    garden.rocks.push(rock);
    rock.onReady = function () {
      $('#rock-caddy').append(rock.canvas);
    };
  });
});
