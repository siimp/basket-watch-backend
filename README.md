# basket-watch-backend

## Supported platforms
http://localhost:8888/static/platforms.json

## Add new platform
Edit file [platforms.json](src/main/resources/static/platforms.json)

Add new json element with structure  
```
  {
    "name": "Platform name",
    "domain": "platform.domain.com",
    "nameJsoupSelector": "div.name",
    "priceJsoupSelector": "div.price",
    "image": "base64-data-here"
  }
```

For more options see class [Platform.java](src/main/java/basket/watch/backend/scraper/Platform.java) and
[ScraperService.java](src/main/java/basket/watch/backend/scraper/ScraperService.java)  
