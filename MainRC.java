public class MainRC {

    public static void main(String[] args) {
        GestorePersistenza gp=new GestorePersistenza(new StrategiaPersistenzaJson());
        Libreria libreria= Libreria.getInstance();
        VistaLibreria vistaLibreria=new VistaLibreriaRC();
        libreria.aggiungiAscoltatore(vistaLibreria);

        ControllerLibreria controller=new ControllerLibreria(libreria, vistaLibreria, gp);
        controller.run();











    }




}
