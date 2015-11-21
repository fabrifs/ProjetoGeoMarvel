var meuMapa;
function init() {
	
	var vector = new ol.layer.Vector({
        source: new ol.source.Vector({
          url: 'dados/pontos.gpx',
          format: new ol.format.GPX()
        }),
        style: function(feature, resolution) {
          return style[feature.getGeometry().getType()];
        }
      });
	
	
    meuMapa = new ol.Map({
	layers: [raster, vector],
        target: 'MeuMapa',
        renderer: 'canvas',
        view: new ol.View({
            projection: 'EPSG:900913',
            center: [-5193252.61684, -2698365.38923],
            zoom: 18
        })
    });
	
	var displayFeatureInfo = function(pixel) {
        var features = [];
        map.forEachFeatureAtPixel(pixel, function(feature, layer) {
          features.push(feature);
        });
        if (features.length > 0) {
          var info = [];
          var i, ii;
          for (i = 0, ii = features.length; i < ii; ++i) {
            info.push(features[i].get('desc'));
          }
          document.getElementById('info').innerHTML = info.join(', ') || '(unknown)';
          map.getTarget().style.cursor = 'pointer';
        } else {
          document.getElementById('info').innerHTML = '&nbsp;';
          map.getTarget().style.cursor = '';
        }
      };
	
    var openStreetMapLayer = new ol.layer.Tile({
        source: new ol.source.OSM()
    });
    meuMapa.addLayer(openStreetMapLayer);
    var iconFeature = new ol.Feature({
        geometry: new ol.geom.Point([-5193252.61684, -2698365.38923]),
        name: 'Fabio'
    });
    var iconStyle = new ol.style.Style({
        image: new ol.style.Icon(({
            anchor: [0.5, 46],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            opacity: 0.75,
            src: 'dados/r2d2.png'
        }))
    });
    iconFeature.setStyle(iconStyle);
    var vectorSource = new ol.source.Vector({
        features: [iconFeature]
    });
    var vectorLayer = new ol.layer.Vector({
        source: vectorSource
    });
    meuMapa.addLayer(vectorLayer);
    var element = document.getElementById('popup');
    var popup = new ol.Overlay({
        element: element,
        positioning: 'bottom-center',
        stopEvent: false
    });
    meuMapa.addOverlay(popup);
    meuMapa.on('click', function (evt) {
        var feature = meuMapa.forEachFeatureAtPixel(evt.pixel,
                function (feature, layer) {
                    return feature;
                });
        if (feature) {
            popup.setPosition(evt.coordinate);
            var xmlString;
            var login = feature.get('name')
            var urlString = 'http://localhost:8080/ModuloWeb/Marvel/marvel/listaPopup/';
            console.log(urlString);
            urlString = urlString.concat(login);
            console.log(urlString);
            $.ajax({
                url: urlString,
                data: {
                    format: 'html'
                },
                success: function (data) {
                    $(element).popover({
                        'placement': 'top',
                        'html': true,
                        'content': '<p>' + data + '</p>'
                    });
                    $(element).popover('show');
                },
                error: function (e) {
                    console.log(e.message);
                },
                type: 'GET'
            });
        } else {
            $(element).popover('destroy');
        }
    });
    meuMapa.on('pointermove', function (e) {
        if (e.dragging) {
            $(element).popover('destroy');
            return;
        }
        var pixel = meuMapa.getEventPixel(e.originalEvent);
        var hit = meuMapa.hasFeatureAtPixel(pixel);
    });
}