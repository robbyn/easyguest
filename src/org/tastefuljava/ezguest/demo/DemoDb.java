package org.tastefuljava.ezguest.demo;
import org.tastefuljava.ezguest.data.Article;
import org.tastefuljava.ezguest.data.ArticleCategory;
import org.tastefuljava.ezguest.data.Customer;
import org.tastefuljava.ezguest.data.Hotel;
import org.tastefuljava.ezguest.data.Invoice;
import org.tastefuljava.ezguest.data.Period;
import org.tastefuljava.ezguest.data.Reservation;
import org.tastefuljava.ezguest.data.Room;
import org.tastefuljava.ezguest.data.RoomType;
import org.tastefuljava.ezguest.data.Tariff;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tastefuljava.ezguest.session.EasyguestSession;
import java.awt.Color;
import org.tastefuljava.ezguest.util.Util;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.hibernate.Query;

public class DemoDb {
    private static Log log = LogFactory.getLog(DemoDb.class);

    /**
     * @param args the command line arguments
     */
    public static void create() {
        EasyguestSession sess = new EasyguestSession();
        try {
            sess.begin();
            try {
                ArticleCategory root = new ArticleCategory();
                root.setName(Util.getResource("demo.artCategory"));
                sess.makePersistent(root);

                ArticleCategory catdrinks = new ArticleCategory();
                catdrinks.setName(Util.getResource("demo.artCategory.drinks")); 
                catdrinks.setParent(root);
                sess.makePersistent(catdrinks);
                
                ArticleCategory subcatdrink0 = new ArticleCategory();
                subcatdrink0.setName(Util.getResource("demo.artCategory.coldDrinks")); 
                subcatdrink0.setParent(catdrinks);
                sess.makePersistent(subcatdrink0);

                Article article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.coldDrinks.code.coca-cola")); 
                article.setLabel(Util.getResource("demo.artCategory.coldDrinks.name.coca-cola")); 
                article.setPrice(3.0); //3.0
                article.setCategory(subcatdrink0);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.coldDrinks.code.pepsi-cola"));
                article.setLabel(Util.getResource("demo.artCategory.coldDrinks.name.pepsi-cola"));
                article.setPrice(3.0);
                article.setCategory(subcatdrink0);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.coldDrinks.code.lemonade"));
                article.setLabel(Util.getResource("demo.artCategory.coldDrinks.name.lemonade"));
                article.setPrice(2.3);
                article.setCategory(subcatdrink0);
                sess.makePersistent(article);
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.coldDrinks.code.perrier")); 
                article.setLabel(Util.getResource("demo.artCategory.coldDrinks.name.perrier")); 
                article.setPrice(2.5);
                article.setCategory(subcatdrink0);
                sess.makePersistent(article);                
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.coldDrinks.code.san-pelligrino"));
                article.setLabel(Util.getResource("demo.artCategory.coldDrinks.name.san-pelligrino")); 
                article.setPrice(2.5);
                article.setCategory(subcatdrink0);
                sess.makePersistent(article);                

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.coldDrinks.code.evian")); 
                article.setLabel(Util.getResource("demo.artCategory.coldDrinks.name.evian")); 
                article.setPrice(2.5);
                article.setCategory(subcatdrink0);
                sess.makePersistent(article);                

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.coldDrinks.code.courmayeur")); 
                article.setLabel(Util.getResource("demo.artCategory.coldDrinks.name.courmayeur")); 
                article.setPrice(2.5);
                article.setCategory(subcatdrink0);
                sess.makePersistent(article);                
                
                ArticleCategory subcatdrink1 = new ArticleCategory();
                subcatdrink1.setName(Util.getResource("demo.artCategory.hotDrinks"));
                subcatdrink1.setParent(catdrinks);
                sess.makePersistent(subcatdrink1);                

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_K, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.hotDrinks.code.coffee")); 
                article.setLabel(Util.getResource("demo.artCategory.hotDrinks.name.coffee"));
                article.setPrice(2.0);
                article.setCategory(subcatdrink1);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.hotDrinks.code.cocoa")); 
                article.setLabel(Util.getResource("demo.artCategory.hotDrinks.name.cocoa"));
                article.setPrice(2.0);
                article.setCategory(subcatdrink1);
                sess.makePersistent(article);
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.hotDrinks.code.tea")); 
                article.setLabel(Util.getResource("demo.artCategory.hotDrinks.name.tea"));
                article.setPrice(2.0);
                article.setCategory(subcatdrink1);
                sess.makePersistent(article);                
                
                ArticleCategory subcatdrink2 = new ArticleCategory();
                subcatdrink2.setName(Util.getResource("demo.artCategory.alcoolDrinks"));
                subcatdrink2.setParent(catdrinks);
                sess.makePersistent(subcatdrink2);
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.whisky"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.whisky"));
                article.setPrice(8.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.cognac"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.cognac"));
                article.setPrice(8.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.vodka"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.vodka"));
                article.setPrice(8.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.tequila"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.tequila"));
                article.setPrice(8.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.gin"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.gin"));
                article.setPrice(8.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.champagne"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.champagne"));
                article.setPrice(8.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.martini"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.martini"));
                article.setPrice(8.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
                article.setCode(Util.getResource("demo.artCategory.alcoolDrinks.code.bier"));
                article.setLabel(Util.getResource("demo.artCategory.alcoolDrinks.name.bier"));
                article.setPrice(5.0);
                article.setCategory(subcatdrink2);
                sess.makePersistent(article);
                
                ArticleCategory catfood = new ArticleCategory();
                catfood.setName(Util.getResource("demo.artCategory.food"));
                catfood.setParent(root);
                sess.makePersistent(catfood);                

                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.SHIFT_MASK));
                article.setCode(Util.getResource("demo.artCategory.food.code.breakfast"));
                article.setLabel(Util.getResource("demo.artCategory.food.name.breakfast"));
                article.setPrice(2.0);
                article.setCategory(catfood);
                sess.makePersistent(article);
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F2, KeyEvent.SHIFT_MASK));
                article.setCode(Util.getResource("demo.artCategory.food.code.tart"));
                article.setLabel(Util.getResource("demo.artCategory.food.name.tart"));
                article.setPrice(7.0);
                article.setCategory(catfood);
                sess.makePersistent(article);                
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.SHIFT_MASK));
                article.setCode(Util.getResource("demo.artCategory.food.code.pastry"));
                article.setLabel(Util.getResource("demo.artCategory.food.name.pastry"));
                article.setPrice(1.0);
                article.setCategory(catfood);
                sess.makePersistent(article);                
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.SHIFT_MASK));
                article.setCode(Util.getResource("demo.artCategory.food.code.sandwich"));
                article.setLabel(Util.getResource("demo.artCategory.food.name.sandwich"));
                article.setPrice(3.5);
                article.setCategory(catfood);
                sess.makePersistent(article); 
                
                article = new Article();
                article.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.SHIFT_MASK));
                article.setCode(Util.getResource("demo.artCategory.food.code.toast"));
                article.setLabel(Util.getResource("demo.artCategory.food.name.toast"));
                article.setPrice(3.7);
                article.setCategory(catfood);
                sess.makePersistent(article); 
                                
                sess.commit();                                
            } finally {
                sess.end();
            }

            sess.begin();
            try {
                Collection col = sess.getExtent(ArticleCategory.class);
                for (Iterator it = col.iterator(); it.hasNext(); ) {
                    ArticleCategory cat = (ArticleCategory)it.next();
                    System.out.println(Util.getResource("demo.category") + cat.getId() + ": " + cat.getName());
                }

                Collection coll = sess.getExtent(Article.class);
                for (Iterator it = coll.iterator(); it.hasNext(); ) {
                    Article art = (Article)it.next();
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");                
                    System.out.println(Util.getResource("demo.artCategory") + art.getId() + ": " + art.getKeyStroke());
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            } finally {
                sess.end();
            }

            int thisYear = Util.year(Util.today());
            Serializable tarifid;
            sess.begin();
            try {
                Tariff tarif = new Tariff();
                tarif.setName(Util.getResource("demo.tariff.period.winterHolidays"));
                tarif.setFactor(1.3);
                tarif.setColor(Color.lightGray);
                tarifid = sess.makePersistent(tarif);

                Period period = new Period();
                period.setTariff(tarif);
                period.setFromDate(Util.makeDate(thisYear-1, 12, 25));
                period.setToDate(Util.makeDate(thisYear, 3, 21));
                sess.makePersistent(period);

                tarif = new Tariff();
                tarif.setName(Util.getResource("demo.tariff.period.middleSeasons"));
                tarif.setFactor(1.1);
                tarif.setColor(Color.green);
                sess.makePersistent(tarif);

                period = new Period();                
                period.setFromDate(Util.makeDate(thisYear, 3, 22));
                period.setToDate(Util.makeDate(thisYear, 6, 21));
                period.setTariff(tarif);
                sess.makePersistent(period);

                tarif = new Tariff();
                tarif.setName(Util.getResource("demo.tariff.period.summerHolidays"));
                tarif.setFactor(1.4);
                tarif.setColor(Color.yellow);
                sess.makePersistent(tarif);

                period = new Period();
                period.setFromDate(Util.makeDate(thisYear, 6, 22));
                period.setToDate(Util.makeDate(thisYear, 9, 21));
                period.setTariff(tarif);
                sess.makePersistent(period);

                tarif = new Tariff();
                tarif.setName(Util.getResource("demo.tariff.period.exceptSeasons"));
                tarif.setFactor(1.0);
                tarif.setColor(Color.orange);
                sess.makePersistent(tarif);

                period = new Period();
                period.setFromDate(Util.makeDate(thisYear, 9, 22));
                period.setToDate(Util.makeDate(thisYear, 12, 24));
                period.setTariff(tarif);                
                sess.makePersistent(period);
                sess.commit();
            } finally {
                sess.end();
            }

            Serializable rtid0;
            sess.begin();
            try {
                Hotel hotel;
                hotel = new Hotel();
                hotel.setRate(Util.getResource("demo.hotel.rate"));
                hotel.setChain(Util.getResource("demo.hotel.chain"));
                hotel.setName(Util.getResource("demo.hotel.name"));
                hotel.setAddress1(Util.getResource("demo.hotel.adress1"));
                hotel.setAddress2(Util.getResource("demo.hotel.adress2"));
                hotel.setZip(Util.getResource("demo.hotel.zip"));
                hotel.setCity(Util.getResource("demo.hotel.city"));
                hotel.setState(Util.getResource("demo.hotel.state"));
                hotel.setCountry(Util.getResource("demo.hotel.country"));
                hotel.setTelephone(Util.getResource("demo.hotel.phone"));
                hotel.setFax(Util.getResource("demo.hotel.fax"));
                hotel.setEmail(Util.getResource("demo.hotel.email"));
                hotel.setWeb(Util.getResource("demo.hotel.web"));
                sess.makePersistent(hotel);

                RoomType roomType0 = new RoomType();
                roomType0.setName(Util.getResource("demo.hotel.roomType.name.0"));
                roomType0.setBasePrice(35.0);
                roomType0.setDescription(Util.getResource("demo.hotel.roomType.description.0"));
                rtid0 = sess.makePersistent(roomType0);
                
                RoomType roomType1 = new RoomType();
                roomType1.setName(Util.getResource("demo.hotel.roomType.name.1"));
                roomType1.setBasePrice(40.0);
                roomType1.setDescription(Util.getResource("demo.hotel.roomType.description.1"));
                sess.makePersistent(roomType1);

                RoomType roomType2 = new RoomType();
                roomType2.setName(Util.getResource("demo.hotel.roomType.name.2"));
                roomType2.setBasePrice(45.0);
                roomType2.setDescription(Util.getResource("demo.hotel.roomType.description.2"));
                sess.makePersistent(roomType2);

                RoomType roomType3 = new RoomType();
                roomType3.setName(Util.getResource("demo.hotel.roomType.name.3"));                
                roomType3.setBasePrice(50.0);
                roomType3.setDescription(Util.getResource("demo.hotel.roomType.description.3"));
                sess.makePersistent(roomType3);

                for (int n = 101; n < 111; ++n) {
                    Room room = new Room();
                    room.setNumber(n);
                    room.setType(roomType0);
                    hotel.addRoom(room);
                    sess.makePersistent(room);
                }
                for (int n = 201; n < 211; ++n) {
                    Room room = new Room();
                    room.setNumber(n);
                    room.setType(roomType1);
                    hotel.addRoom(room);
                    sess.makePersistent(room);
                }
                for (int n = 301; n < 311; ++n) {
                    Room room = new Room();
                    room.setNumber(n);
                    room.setType(roomType2);
                    hotel.addRoom(room);
                    sess.makePersistent(room);
                }
                for (int n = 401; n < 411; ++n) {
                    Room room = new Room();
                    room.setNumber(n);
                    room.setType(roomType3);
                    hotel.addRoom(room);
                    sess.makePersistent(room);
                }

                sess.update(hotel);
                sess.commit();
            } finally {
                sess.end();
            }

            Serializable custid0;
            Serializable custid1;
            Serializable custid2;
            Serializable custid3;            

            sess.begin();
            try {
                Customer customer0 = new Customer();
                customer0.setCompany(Util.getResource("demo.hotel.customer.company0"));
                customer0.setTitlePerson(Util.getResource("demo.hotel.customer.titleperson0"));
                customer0.setLastName(Util.getResource("demo.hotel.customer.lastname0"));
                customer0.setFirstName(Util.getResource("demo.hotel.customer.firstname0"));
                customer0.setAddress1(Util.getResource("demo.hotel.customer.adress1-0"));
                customer0.setAddress2(Util.getResource("demo.hotel.customer.adress2-0"));
                customer0.setZip(Util.getResource("demo.hotel.customer.zip0"));
                customer0.setCity(Util.getResource("demo.hotel.customer.city0"));
                customer0.setState(Util.getResource("demo.hotel.customer.state0"));
                customer0.setCountry(Util.getResource("demo.hotel.customer.country0"));
                customer0.setTelephone(Util.getResource("demo.hotel.customer.phone0"));
                customer0.setMobile(Util.getResource("demo.hotel.customer.mobile0"));
                customer0.setFax(Util.getResource("demo.hotel.customer.fax0"));
                customer0.setEmail(Util.getResource("demo.hotel.customer.email0"));
                custid0 = sess.makePersistent(customer0);

                Customer customer1 = new Customer();
                customer1.setCompany(Util.getResource("demo.hotel.customer.company1"));
                customer1.setTitlePerson(Util.getResource("demo.hotel.customer.titleperson1"));
                customer1.setLastName(Util.getResource("demo.hotel.customer.lastname1"));
                customer1.setFirstName(Util.getResource("demo.hotel.customer.firstname1"));
                customer1.setAddress1(Util.getResource("demo.hotel.customer.adress1-1"));
                customer1.setAddress2(Util.getResource("demo.hotel.customer.adress2-1"));
                customer1.setZip(Util.getResource("demo.hotel.customer.zip1"));
                customer1.setCity(Util.getResource("demo.hotel.customer.city1"));
                customer1.setState(Util.getResource("demo.hotel.customer.state1"));
                customer1.setCountry(Util.getResource("demo.hotel.customer.country1"));
                customer1.setTelephone(Util.getResource("demo.hotel.customer.phone1"));
                customer1.setMobile(Util.getResource("demo.hotel.customer.mobile1"));
                customer1.setFax(Util.getResource("demo.hotel.customer.fax1"));
                customer1.setEmail(Util.getResource("demo.hotel.customer.email1"));
                custid1 = sess.makePersistent(customer1);

                Customer customer2 = new Customer();
                customer2.setCompany(Util.getResource("demo.hotel.customer.company2"));
                customer2.setTitlePerson(Util.getResource("demo.hotel.customer.titleperson2"));
                customer2.setLastName(Util.getResource("demo.hotel.customer.lastname2"));
                customer2.setFirstName(Util.getResource("demo.hotel.customer.firstname2"));
                customer2.setAddress1(Util.getResource("demo.hotel.customer.adress1-2"));
                customer2.setAddress2(Util.getResource("demo.hotel.customer.adress2-2"));
                customer2.setZip(Util.getResource("demo.hotel.customer.zip2"));
                customer2.setCity(Util.getResource("demo.hotel.customer.city2"));
                customer2.setState(Util.getResource("demo.hotel.customer.state2"));
                customer2.setCountry(Util.getResource("demo.hotel.customer.country2"));
                customer2.setTelephone(Util.getResource("demo.hotel.customer.phone2"));
                customer2.setMobile(Util.getResource("demo.hotel.customer.mobile2"));
                customer2.setFax(Util.getResource("demo.hotel.customer.fax2"));
                customer2.setEmail(Util.getResource("demo.hotel.customer.email2"));
                custid2 = sess.makePersistent(customer2);

                Customer customer3 = new Customer();
                customer3.setCompany(Util.getResource("demo.hotel.customer.company3"));
                customer3.setTitlePerson(Util.getResource("demo.hotel.customer.titleperson3"));
                customer3.setLastName(Util.getResource("demo.hotel.customer.lastname3"));
                customer3.setFirstName(Util.getResource("demo.hotel.customer.firstname3"));
                customer3.setAddress1(Util.getResource("demo.hotel.customer.adress1-3"));
                customer3.setAddress2(Util.getResource("demo.hotel.customer.adress2-3"));
                customer3.setZip(Util.getResource("demo.hotel.customer.zip3"));
                customer3.setCity(Util.getResource("demo.hotel.customer.city3"));
                customer3.setState(Util.getResource("demo.hotel.customer.state3"));
                customer3.setCountry(Util.getResource("demo.hotel.customer.country3"));
                customer3.setTelephone(Util.getResource("demo.hotel.customer.phone3"));
                customer3.setMobile(Util.getResource("demo.hotel.customer.mobile3"));
                customer3.setFax(Util.getResource("demo.hotel.customer.fax3"));
                customer3.setEmail(Util.getResource("demo.hotel.customer.email3"));
                custid3 = sess.makePersistent(customer3);

                sess.commit();
            } finally {
                sess.end();
            }

            sess.begin();
            try {
                Hotel hotel = sess.getHotel();
                Room room0 = sess.getRoom(hotel, 101);
                Reservation reservation0 = new Reservation();
                reservation0.setRoom(room0);
                reservation0.setFromDate(Util.today());
                reservation0.setToDate(Util.addDays(Util.today(), 10));
                reservation0.setStatus(Reservation.STATUS_RESERVED);
                sess.makePersistent(reservation0);
                Invoice invoice = new Invoice();
                sess.makePersistent(invoice);
                reservation0.setInvoice(invoice);
                invoice.setHotel(hotel);
                invoice.setCustomer(sess.getObjectById(Customer.class, custid0));
                
                Room room1 = sess.getRoom(hotel, 102);
                Reservation reservation1 = new Reservation();
                reservation1.setRoom(room1);
                reservation1.setFromDate(Util.today());
                reservation1.setToDate(Util.addDays(Util.today(), 10));
                reservation1.setStatus(Reservation.STATUS_RESERVED);
                sess.makePersistent(reservation1);
                invoice = new Invoice();
                sess.makePersistent(invoice);
                reservation1.setInvoice(invoice);
                invoice.setHotel(hotel);
                invoice.setCustomer(sess.getObjectById(Customer.class, custid1));

                Room room2 = sess.getRoom(hotel, 103);
                Reservation reservation2 = new Reservation();
                reservation2.setRoom(room2);
                reservation2.setFromDate(Util.today());
                reservation2.setToDate(Util.addDays(Util.today(), 10));
                reservation2.setStatus(Reservation.STATUS_RESERVED);
                sess.makePersistent(reservation2);
                invoice = new Invoice();
                sess.makePersistent(invoice);
                reservation2.setInvoice(invoice);
                invoice.setHotel(hotel);
                invoice.setCustomer(sess.getObjectById(Customer.class, custid2));
                                
                Room room3 = sess.getRoom(hotel, 104);
                Reservation reservation3 = new Reservation();
                reservation3.setRoom(room3);
                reservation3.setFromDate(Util.today());
                reservation3.setToDate(Util.addDays(Util.today(), 10));
                reservation3.setStatus(Reservation.STATUS_RESERVED);
                sess.makePersistent(reservation3);
                invoice = new Invoice();
                sess.makePersistent(invoice);
                reservation3.setInvoice(invoice);
                invoice.setHotel(hotel);
                invoice.setCustomer(sess.getObjectById(Customer.class, custid3));

                sess.commit();
            } finally {
                sess.end();
            }

            sess.begin();
            try {
                System.out.println("CUSTOMER : ");
                for (Iterator it = sess.getExtent(Customer.class).iterator(); it.hasNext(); ) {
                    Customer cust = (Customer)it.next();
                    System.out.println("company: " + cust.getCompany());
                    System.out.println("titleperson: " + cust.getTitlePerson());
                    System.out.println("name: " + cust.getLastName());
                    System.out.println("firstname: " + cust.getFirstName());
                    System.out.println("address1: " + cust.getAddress1());
                    System.out.println("address2: " + cust.getAddress2());
                    System.out.println("zip: " + cust.getZip());
                    System.out.println("city : " + cust.getCity());
                    System.out.println("state : " + cust.getState());
                    System.out.println("country : " + cust.getCountry());
                    System.out.println("telephone : " + cust.getTelephone());
                    System.out.println("mobile : " + cust.getMobile());
                    System.out.println("fax : " + cust.getFax());
                    System.out.println("email : " + cust.getEmail());
                    System.out.println("+ + + + + + + + + + + + + + + + +");
                }
                System.out.println("");

                System.out.println("ROOM TYPE : ");
                RoomType roomType = sess.getObjectById(RoomType.class, rtid0);
                System.out.println("code: " + roomType.getId());
                System.out.println("name: " + roomType.getName());
                System.out.println("base price: " + roomType.getBasePrice());
                //roomType0.setBasePrice(roomType0.getBasePrice()*tarif.getFactor());
                sess.commit();
            } finally {
                sess.end();
            }


            sess.begin();
            try {
                System.out.println("TARIFF : ");
                Tariff tarif = sess.getObjectById(Tariff.class, tarifid);
                System.out.println("code: " + tarif.getId());
                System.out.println("name: " + tarif.getName());
                System.out.println("factor: " + tarif.getFactor());
                System.out.println("");
                System.out.println("ROOM TYPE : ");
                RoomType rt = sess.getObjectById(RoomType.class, rtid0);
                System.out.println("code: " + rt.getId());
                System.out.println("name: " + rt.getName());
                System.out.println("base price: " + rt.getBasePrice());
                //roomType1.setBasePrice(roomType1.getBasePrice()*tarif.getFactor());
                sess.commit();
            } finally {
                sess.end();
            }

            sess.begin();
            try {
                System.out.println("PERIOD : ");
                Tariff tarif = sess.getObjectById(Tariff.class, tarifid);
                System.out.println("code: " + tarif.getId());
                System.out.println("name: " + tarif.getName());
                System.out.println("factor: " + tarif.getFactor());
                System.out.println("");
                for (Iterator it = sess.getExtent(Period.class).iterator(); it.hasNext(); ) {
                    Period period = (Period)it.next();
                    System.out.println("fromDate: " + Util.date2str(period.getFromDate()));
                    System.out.println("toDate: " + Util.date2str(period.getToDate()));
                    System.out.println("tarif code: " + period.getTariff().getId());
                    System.out.println("---");
                }
                System.out.println("");
                System.out.println("ROOM : ");
                RoomType rt = sess.getObjectById(RoomType.class, rtid0);
                System.out.println("code: " + rt.getId());
                System.out.println("name: " + rt.getName());
                System.out.println("base price: " + rt.getBasePrice());
                System.out.println("description: " + rt.getDescription());
                System.out.println("");
                for (Iterator it = sess.getExtent(Room.class).iterator(); it.hasNext(); ) {
                    Room r = (Room)it.next();
                    System.out.println("hotel: " + r.getHotel());
                    System.out.println("number: " + r.getNumber());
                    System.out.println("type code: " + r.getType().getId());
                    System.out.println("---");
                }
                System.out.println("");
            } finally {
                sess.end();
            }

            sess.begin();
            try {
                Query qry = sess.getPm().createQuery("from Tariff as t where t.factor < :maxFactor");
                qry.setDouble("maxFactor", 2.3);
                Collection col = qry.list();
                for (Iterator it = col.iterator(); it.hasNext(); ) {
                    Tariff tarif = (Tariff)it.next();
                    System.out.println("code: " + tarif.getId());
                    System.out.println("name: " + tarif.getName());
                    System.out.println("factor: " + tarif.getFactor());
                    Iterator it2 = tarif.getPeriods().iterator();
                    while (it2.hasNext()) {
                        Period period = (Period)it2.next();
                        System.out.println("  fromDate: " + Util.date2str(period.getFromDate()));
                        System.out.println("  toDate: " + Util.date2str(period.getToDate()));
                    }
                    System.out.println("---");
                }
                System.out.println("");
            } finally {
                sess.end();
            }

            sess.begin();
            try {
                Query qry = sess.getPm().createQuery("from Tariff as t where t.factor < :maxFactor");
                qry.setDouble("maxFactor", 1.3);
                for (Iterator it = qry.iterate(); it.hasNext(); ) {
                    Tariff tarif = (Tariff)it.next();
                    System.out.println("code: " + tarif.getId());
                    System.out.println("name: " + tarif.getName());
                    System.out.println("factor: " + tarif.getFactor());
                    Iterator it2 = tarif.getPeriods().iterator();
                    while (it2.hasNext()) {
                        Period period = (Period)it2.next();
                        System.out.println("  fromDate: " + Util.date2str(period.getFromDate()));
                        System.out.println("  toDate: " + Util.date2str(period.getToDate()));
                    }
                    System.out.println("---");
                }
                System.out.println("");
                    System.out.println("RESERVATION : ");
                    for (Iterator it = sess.getExtent(Reservation.class).iterator(); it.hasNext(); ) {
                        Reservation r = (Reservation)it.next();
                        System.out.println("  room : " + r.getRoom());
                        System.out.println("  fromDate: " + Util.date2str(r.getFromDate()));
                        System.out.println("  toDate: " + Util.date2str(r.getToDate()));
                        System.out.println("  status : " + r.getStatus());
                        System.out.println("---");
                    }
            } finally {
                sess.end();
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }
}
