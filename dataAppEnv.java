package dataApp;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.KeyGenerator;

import ail.syntax.Action;
import ail.syntax.Message;
import ail.syntax.NumberTermImpl;
import ail.syntax.Predicate;
import ail.syntax.Unifier;
import ail.util.AILexception;
import gwendolen.mas.VerificationofAutonomousSystemsEnvironment;

public class dataAppEnv extends VerificationofAutonomousSystemsEnvironment {
	// belief(workout_plan(present))
	// belief(diet_recommendations(present))
	// belief(daily_physical_activities(monitored))
	boolean workout_plan_present_sent = true;
	boolean diet_recommendations_present_sent = true;
	boolean daily_physical_activities_monitored_sent = true;

	//data(full_name)
	//data(dob) *
	//data(gender)
	//data(bmi) *
	//data(contact)
	//data(home_address)
	//data(marital_status) *
	//data(occupation) *
	//data(education) *
	//data(gps) *
	//data(social_media)
	
	boolean started = false;
	boolean returns = false;
	int count = 0;
	Key key;
	boolean name_req = true;
	boolean gps_req = true;
	boolean address_req = false;
	boolean age_req = true;
	boolean dob_req = true;
	boolean mar_req = true;


	@Override
	public Set<Predicate> generate_percepts() {
		// TODO Auto-generated method stub
		return new HashSet<Predicate>();
	}

	@Override
	public Set<Message> generate_messages() {
		// TODO Auto-generated method stub
		Set<Message> messages = new HashSet<Message>();

		if (!started) {
			started = true;
			UserManager usermanager = new UserManager();
			Agent123 agent123 = new Agent123();
			usermanager.start();
			Main main = new Main();
			try {
				returns = main.main(null);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}	
		



				Predicate full_name = new Predicate("request");
				full_name.addTerm(new Predicate("full_name"));
				messages.add(new Message(1, "user", "agent", full_name));

			
		



				Predicate dob = new Predicate("request");
				dob.addTerm(new Predicate("dob"));
				messages.add(new Message(1, "user", "agent", dob));

			
		



			if (address_req) {
				Predicate home_address = new Predicate("request");
				home_address.addTerm(new Predicate("home_address"));
				messages.add(new Message(1, "user", "agent", home_address));
			}
			
	



	
				Predicate marital_status = new Predicate("request");
				marital_status.addTerm(new Predicate("marital_status"));
				messages.add(new Message(1, "user", "agent", marital_status));





				Predicate gps = new Predicate("request");
				gps.addTerm(new Predicate("gps"));
				messages.add(new Message(1, "user", "agent", gps));
			
	
		
		return messages;

	}
	
    public Unifier executeAction(String agName, Action act) throws AILexception {
	   	Unifier theta = new Unifier();
	   		   		   	
	   		   	if (act.getFunctor().equals("send")) {
	   		Predicate object = (Predicate) act.getTerm(0);
	   		if (object.getFunctor().equals("decline")) {
	   			Agent123.removedatapoint(object.getTerm(0), returns);
	   		} else if (object.getFunctor().equals("change")) {
	   			Agent123.changeData(object.getTerm(0), returns);
	   		}
	   			   		
	   	}	   		   	
	   	return theta;
    }

}