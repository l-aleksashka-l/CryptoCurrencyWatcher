package financeid;

import financeid.model.Crypto;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static financeid.ProcessingAPI.*;
import static financeid.ProcessingAPI.getPrice;

public class Start {
    public static URL urlETH, urlBTC, urlSOL;

    {
        try {
             urlETH = new URL("https://api.coinlore.net/api/ticker/?id=80");String nameETH = getName(urlETH);String symbolETH = getSymbol(urlETH);double priceETH = getPrice(urlETH);
             urlBTC = new URL("https://api.coinlore.net/api/ticker/?id=90");String nameBTC = getName(urlBTC);String symbolBTC = getSymbol(urlBTC);double priceBTC = getPrice(urlBTC);
             urlSOL = new URL("https://api.coinlore.net/api/ticker/?id=48543");String nameSOL = getName(urlSOL);String symbolSOL = getSymbol(urlSOL);double priceSOL = getPrice(urlSOL);

            List<Crypto> sixTimes = new ArrayList<>();
            sixTimes.add(new Crypto(symbolBTC, nameBTC,priceBTC));
            sixTimes.add(new Crypto(symbolETH, nameETH,priceETH));
            sixTimes.add(new Crypto(symbolSOL, nameSOL,priceSOL));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
