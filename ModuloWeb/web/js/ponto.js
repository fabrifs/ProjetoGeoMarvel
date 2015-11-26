var meuMapa;
function init() {

    meuMapa = new ol.Map({
        target: MeuMapa,
        renderer: 'canvas',
        view: new ol.View({
            projection: 'EPSG:900913',
            center: [-5193252.61684, -2698365.38923],
            zoom: 18
        })
    });

    var openStreetMapLayer = new ol.layer.Tile({
        source: new ol.source.OSM()
    });
    meuMapa.addLayer(openStreetMapLayer);


    var bingLayer = new ol.layer.Tile({
        source: new ol.source.BingMaps({
            imagerySet: 'Aerial',
            key: 'Ak-dzM4wZjSqTlzveKz5u0d4IQ4bRzVI309GxmkgSVr1ewS6iPSrOvOKhACJlm3'
        })
    });
    bingLayer.setOpacity(.3);
    meuMapa.addLayer(bingLayer);


    var pointStyle = new ol.style.Style({
        image: new ol.style.Icon(({
            anchor: [0, 0],
            anchorXUnits: 'fraction',
            anchorYUnits: 'fraction',
            opacity: 0.75,
            src: 'dados/r2d2.png'
        }))
    });
    var layergpx = new ol.layer.Vector({
        source: new ol.source.Vector({
            projection: 'EPSG:4326',
            format: new ol.format.GPX(),
            url: 'dados/pontos.gpx'
        }),
        style: pointStyle
    });
    meuMapa.addLayer(layergpx);

    var element = document.getElementById('popup');
    var popup = new ol.Overlay({
        element: element,
        positioning: 'bottom-center',
        stopEvent: false
        
    });
    meuMapa.addOverlay(popup);

    /*
    meuMapa.on('click', function (evt) {
        var feature = meuMapa.forEachFeatureAtPixel(evt.pixel,
                function (feature, layer) {
                    return feature;
                });
        if (feature) {
            popup.setPosition(evt.coordinate);
            $(element).popover({
                'placement': 'top',
                'html': true,
                'content': feature.get('name')
            });
            $(element).popover('show');
        } else {
            $(element).popover('destroy');
        }
    });
    */
     meuMapa.on('click', function (evt) {
        var feature = meuMapa.forEachFeatureAtPixel(evt.pixel,
                function (feature, layer) {
                    return feature;
                });
        if (feature) {
            popup.setPosition(evt.coordinate);
            var login = feature.get('name');
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