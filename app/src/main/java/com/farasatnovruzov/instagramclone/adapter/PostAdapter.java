package com.farasatnovruzov.instagramclone.adapter;

import java.sql.Timestamp;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.farasatnovruzov.instagramclone.databinding.RecyclerRowBinding;
import com.farasatnovruzov.instagramclone.model.Post;
import com.farasatnovruzov.instagramclone.view.FeedActivity;
import com.farasatnovruzov.instagramclone.view.UploadActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private ArrayList<Post> postArrayList;
    AlertDialog.Builder alert;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth auth;
    private StorageReference storageReference;
    CollectionReference docRef;
    DocumentReference documentReference;


    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();


        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.recyclerRowBinding.recyclerViewDownloadText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alert = new AlertDialog.Builder(holder.itemView.getContext());
                alert.setTitle("Delete Post");
                alert.setMessage("Are you sure delete?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseFirestore.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Toast.makeText(holder.itemView.getContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                                if (value != null) {
//                                    DocumentSnapshot documentSnapshot = value.getDocuments();
//                                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
//                                        System.out.println(documentSnapshot.getId());
//                                        String docID = documentSnapshot.getId();
//                                        System.out.println(postArrayList.get(position).docId);

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user != null) {
                                                // Id of the provider (ex: google.com)
//                                                String providerId = profile.getProviderId();
                                                // UID specific to the provider
//                                                String uid = profile.getUid();
                                                // Name, email address, and profile photo Url
                                                String email = user.getEmail();
//                                                System.out.println(email);
                                                if (email.equals(postArrayList.get(position).email)) {
                                                    firebaseFirestore.collection("Posts").document(postArrayList.get(position).docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
//                                                            Toast.makeText(holder.itemView.getContext(), "Post has successfully deleted", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(holder.itemView.getContext(), FeedActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            holder.itemView.getContext().startActivity(intent);



                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(holder.itemView.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                }
                                                else if(!(email.equals(postArrayList.get(position).email))) {
//                                                    Toast.makeText(holder.itemView.getContext(), "Not allowed to delete other user's post!", Toast.LENGTH_SHORT).show();
//                                                    Intent intent = new Intent(holder.itemView.getContext(), FeedActivity.class);
//                                                    holder.itemView.getContext().startActivity(intent);
//                                                    Toast.makeText(holder.itemView.getContext(), "Not Deleted", Toast.LENGTH_LONG).show();

                                                }


                                        }


                                }


                            }

                        });


                    }


                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(holder.itemView.getContext(), "Not Deleted", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(holder.itemView.getContext(), FeedActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                alert.show();
                return true;
            }
        });


        holder.recyclerRowBinding.recyclerViewUserEmailText.setText(postArrayList.get(position).email);
        holder.recyclerRowBinding.recyclerViewDateText.setText(postArrayList.get(position).postDate);
        holder.recyclerRowBinding.recyclerViewCommentText.setText(postArrayList.get(position).postComment);
        holder.recyclerRowBinding.recyclerViewDownloadText.setText(postArrayList.get(position).downloadUrl);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerViewImageView);


    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

        RecyclerRowBinding recyclerRowBinding;

        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }

}


