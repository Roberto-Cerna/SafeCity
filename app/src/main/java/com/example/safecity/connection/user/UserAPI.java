package com.example.safecity.connection.user;

import com.example.safecity.ui.login.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    // Get info of user
    // Parameters: Id of user
    // Req: -
    // Returns: Info of user
    @GET("user/info/{id}")
    Call<InfoResult> getInfo(@Path("id") String id);

    // Update User's Profile
    // Parameters: Id of user
    // Body: User's profile changes
    // Returns: Message of the process
    @PUT("user/profile/{id}")
    Call<DefaultResult> updateProfile(@Path("id") String id, @Body UpdateProfileBody updateProfileBody);

    // Post Emergency Contacts
    // // Parameters: -
    // // Req: User id, contact_name, contact_phone
    // // Returns: Array of emergency contacts of user
    // router.post("/emergency_contacts/", async (req, res) => {
    //   let user_id = req.body.id;
    //   let contact_name = req.body.contact_name;
    //   let contact_phone = req.body.contact_phone;
    //   let newContact;
    //   await User.findOne({ phone: contact_name }, "photo")
    //     .then((contact_photo) => {
    //       newContact = {
    //         name: contact_name,
    //         phone: contact_phone,
    //         photo: contact_photo,
    //       };
    //     })
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    //   await User.findByIdAndUpdate(user_id, {
    //     $push: { emergencyContacts: newContact },
    //   })
    //     .then((user) => res.status(200).json({ msg: "Contacto agregado" }))
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    // });

    // // Get Emergency Contacts
    // // Parameters: Id of user
    // // Req: -
    // // Returns: Array of emergency contacts of user
    // router.get("/emergency_contacts/:id", async (req, res) => {
    //   let user_id = req.params.id;
    //   await User.findOne({ _id: user_id }, "emergencyContacts")
    //     .then((result) => {
    //       res.status(200).json({ emergencyContacts: result.emergencyContacts });
    //     })
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    // });

    // // Delete Emergency Contact
    // // Parameters: Id of user
    // // Body: Phone of emergency contact to be dropped
    // // Returns: Message of the process
    // router.put("/delete_emergency_contacts/:id", async (req, res) => {
    //   let user_id = req.params.id;
    //   let contact_phone = req.body.contact_phone;
    //   await User.findByIdAndUpdate(user_id, {
    //     $pull: { emergencyContacts: { phone: contact_phone } },
    //   })
    //     .then((result) => {
    //       res.status(200).json({ msg: "Contacto eliminado" });
    //     })
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    // });

    // // Get reports sent to the user
    // // Parameters: Id of user
    // // Body: -
    // // Returns: Array with the last 20 reports sent to the user within last 7 days
    // router.get("/received_reports/:id", async (req, res) => {
    //   let user_id = req.params.id;
    //   let received_reports = [];
    //   await User.findById(user_id)
    //     .then((user) => {
    //       received_reports = user.reports;
    //     })
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    //   await Report.find({ _id: { $in: received_reports } })
    //     .then((result) => res.status(200).json({ received_reports: result }))
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    // });

    // // Put attending incident
    // // Parameters: -
    // // Req: User id, report id
    // // Returns: Message of the process
    // router.put("/attend/", async (req, res) => {
    //   let user_id = req.body.id;
    //   let report_id = req.body.report_id;
    //   //agregar ubicacion del reporte
    //   // await User.findByIdAndUpdate(user_id, {
    //   //   $push: {attending: user_id}
    //   // }).then((user) => {}).catch((err) => res.status(500).json({ err: err.toString() }));
    //   await Report.findByIdAndUpdate(report_id, {
    //     $push: { attending: user_id },
    //   })
    //     .then((report) => res.status(200).json({ msg: "Atendiendo incidente" }))
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    // });

    // // Get attending locations
    // // Parameters: Id of user
    // // Req: -
    // // Returns: Message of the process
    // router.get("/attending_locations/:id", async (req, res) => {
    //   let user_id = req.params.id;
    //   await User.findById(user_id)
    //     .then((user) =>
    //       res.status(200).json({ attending_locations: user.attending_locations })
    //     )
    //     .catch((err) => res.status(500).json({ err: err.toString() }));
    // });
}
