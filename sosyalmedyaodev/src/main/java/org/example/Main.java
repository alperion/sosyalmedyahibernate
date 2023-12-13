package org.example;

import org.example.controller.CommentController;
import org.example.controller.LikeController;
import org.example.controller.PostController;
import org.example.controller.UserController;
import org.example.entity.Comments;
import org.example.entity.Posts;
import org.example.entity.UserProfile;
import org.example.repository.UserRepository;
import org.example.utility.CrudRepository;
import org.example.utility.Singleton;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        CrudRepository<UserProfile,Integer> crudRepository=new CrudRepository();
        UserController userController=Singleton.getUserController();
        PostController postController=Singleton.getPostController();
        LikeController likeController=Singleton.getLikeController();
        CommentController commentController=Singleton.getCommentController();

        Scanner scannerst=new Scanner(System.in);
        Scanner scannerint=new Scanner(System.in);


        boolean anaDongu=true;
        boolean altDongu1=true;
        int secenek1 ,secenek2,secenek3;
        String anaKullaniciAdi;
        String anaKullaniciSifre;
        String postName;




        while(anaDongu){

            System.out.println("Kullanıcı adını giriniz");
            anaKullaniciAdi=scannerst.nextLine();
            System.out.println("Kullanıcı şifre giriniz");
            anaKullaniciSifre=scannerst.nextLine();

            UserProfile anaKullanici=userController.isExist(anaKullaniciAdi,anaKullaniciSifre);


            if(anaKullanici.getId()!=0){

                        while (altDongu1){
                            anaDongu=false;
                            System.out.println("1-> Post At");
                            System.out.println("2-> Post Yorumla ");
                            System.out.println("3-> Post Beğen ");
                            System.out.println("4-> Post Yorumlarını Gör");
                            System.out.println("5->Çıkış");


                            secenek1=scannerint.nextInt();
                            switch (secenek1){
                                case 1:
                                    System.out.println("Post içeriğini yazınız");
                                    postName=scannerst.nextLine();
                                    postController.postSave(postName, anaKullanici.getId());
                                    break;
                                case 2:
                                    List<Posts> postsList=postController.getAll();
                                    UserProfile userProfile;
                                    for (int i=0;i<postsList.size();i++){

                                        userProfile=userController.getById(postsList.get(i).getOwnerId());
                                        System.out.println("-----------------------------------------------------------------------------------------");
                                        System.out.println("Post => "+postsList.get(i).getPostName() +"   Kullanıcı adı "+userProfile.getUsername()+" Post Id "+postsList.get(i).getId());
                                        System.out.println("-----------------------------------------------------------------------------------------");
                                    }
                                    System.out.println("Yorumlamak istediğiniz Postun Id si ni giriniz ");
                                    secenek2=scannerint.nextInt();
                                    System.out.println("Yorumunuz ? ");
                                    String comment=scannerst.nextLine();

                                    Posts posts1;
                                    posts1=postController.getById(secenek2);
                                    if(posts1.getId()==secenek2){

                                            commentController.save(comment, anaKullanici.getId(),secenek2);
                                    }
                                    else{
                                        System.out.println("Yanlış ID kanka");
                                    }
                                    break;
                                case 3:
                                    List<Posts> postsList1=postController.getAll();
                                    UserProfile userProfile1;
                                    for (int i=0;i<postsList1.size();i++){

                                        userProfile=userController.getById(postsList1.get(i).getOwnerId());
                                        System.out.println("-----------------------------------------------------------------------------------------");
                                        System.out.println("Post => "+postsList1.get(i).getPostName() +"   Kullanıcı adı "+userProfile.getUsername()+" Post Id "+postsList1.get(i).getId());
                                        System.out.println("-----------------------------------------------------------------------------------------");
                                    }
                                    System.out.println("Beğenmek istediğiniz Postun Id si ni giriniz ");
                                    secenek3=scannerint.nextInt();

                                    Posts posts2;
                                    posts2=postController.getById(secenek3);
                                    if(posts2.getId()==secenek3){

                                        likeController.save(anaKullanici.getId(), secenek3);
                                    }
                                    else{
                                        System.out.println("Yanlış ID kanka");
                                    }
                                    break;
                                case 4:
                                        List<UserProfile> userProfileList=userController.getAll();
                                        List<Posts> postsList2=postController.getAll();
                                        List<Comments> commentsList=commentController.getAll();

                                        for(int i=0;i<userProfileList.size();i++){
                                            for(int j=0;j<postsList2.size();j++){
                                                if(userProfileList.get(i).getId()==postsList2.get(j).getOwnerId()){
                                                    System.out.println("*************************************************");
                                                    System.out.print("Kullanıcı adı : ");
                                                    System.out.println(userProfileList.get(i).getUsername());
                                                    System.out.print("Post :: ");
                                                    System.out.println(postsList2.get(j).getPostName());
                                                    for(int z=0;z<commentsList.size();z++){
                                                            if(postsList2.get(j).getId()==commentsList.get(z).getPostId()){
                                                                System.out.println("                    Yorum ");
                                                                System.out.println("                    "+commentsList.get(z).getComments());
                                                            }
                                                    }
                                                }
                                            }
                                        }


                                    break;
                                case 5:
                                    altDongu1=false;
                                    break;
                                default:
                                    System.out.println("alperen efsanedir");

                            }
                        }
            }
            else{

                System.out.println("KULLANICI BULUNAMADI  KAYIT OLUNUZ");
            }
        }









    }
}